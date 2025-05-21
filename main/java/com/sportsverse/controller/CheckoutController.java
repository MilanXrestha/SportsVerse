package com.sportsverse.controller;

import com.sportsverse.model.*;
import com.sportsverse.service.*;
import com.sportsverse.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {
    private final CartService cartService = new CartService();
    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer userId = (Integer) SessionUtil.getAttribute(request, "userId");
            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            List<CartModel> cartItems = cartService.getCartItems(userId);
            if (cartItems.isEmpty()) {
                SessionUtil.setAttribute(request, "checkoutMessage", "Your cart is empty.");
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            List<ProductModel> cartProducts = new ArrayList<>();
            double subtotal = 0.0;
            for (CartModel item : cartItems) {
                ProductModel product = cartService.getProductById(item.getProductId());
                cartProducts.add(product);
                subtotal += product.getProductPrice() * item.getCartQuantity();
            }
            double tax = subtotal * 0.10;
            double total = subtotal + tax;

            List<AddressModel> allAddresses = orderService.getUserAddresses(userId);
            List<AddressModel> shippingAddresses = new ArrayList<>();
            for (AddressModel address : allAddresses) {
                if ("Shipping".equals(address.getAddressType())) {
                    shippingAddresses.add(address);
                }
            }
            boolean hasShippingAddress = !shippingAddresses.isEmpty();

            List<PaymentMethodModel> paymentMethods = orderService.getAllPaymentMethods();

            request.setAttribute("cartItems", cartItems);
            request.setAttribute("cartProducts", cartProducts);
            request.setAttribute("subtotal", subtotal);
            request.setAttribute("tax", tax);
            request.setAttribute("total", total);
            request.setAttribute("addresses", shippingAddresses);
            request.setAttribute("hasShippingAddress", hasShippingAddress);
            request.setAttribute("paymentMethods", paymentMethods);
            request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            SessionUtil.setAttribute(request, "checkoutMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer userId = (Integer) SessionUtil.getAttribute(request, "userId");
            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            String action = request.getParameter("action");
            if ("addAddress".equals(action)) {
                AddressModel address = new AddressModel();
                address.setUserId(userId);
                address.setStreetAddress(request.getParameter("streetAddress"));
                address.setCity(request.getParameter("city"));
                address.setNepalState(request.getParameter("nepalState"));
                address.setAddressType("Shipping"); // Force Shipping type

                if (address.getStreetAddress() == null || address.getCity() == null || address.getNepalState() == null) {
                    throw new IllegalArgumentException("All address fields are required.");
                }

                boolean success = orderService.addAddress(address);
                SessionUtil.setAttribute(request, "checkoutMessage", success ? "Address added successfully." : "Failed to add address.");
                response.sendRedirect(request.getContextPath() + "/checkout");
                return;
            } else if ("updateAddress".equals(action)) {
                AddressModel address = new AddressModel();
                address.setUserId(userId);
                String addressIdStr = request.getParameter("addressId");
                if (addressIdStr == null || addressIdStr.isEmpty()) {
                    throw new IllegalArgumentException("Address ID is required for update.");
                }
                address.setAddressId(Integer.parseInt(addressIdStr));
                address.setStreetAddress(request.getParameter("streetAddress"));
                address.setCity(request.getParameter("city"));
                address.setNepalState(request.getParameter("nepalState"));
                address.setAddressType("Shipping");

                if (address.getStreetAddress() == null || address.getCity() == null || address.getNepalState() == null) {
                    throw new IllegalArgumentException("All address fields are required.");
                }

                boolean success = orderService.updateAddress(address);
                SessionUtil.setAttribute(request, "checkoutMessage", success ? "Address updated successfully." : "Failed to update address.");
                response.sendRedirect(request.getContextPath() + "/checkout");
                return;
            }

            List<CartModel> cartItems = cartService.getCartItems(userId);
            if (cartItems.isEmpty()) {
                SessionUtil.setAttribute(request, "checkoutMessage", "Your cart is empty.");
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            String addressIdStr = request.getParameter("addressId");
            String paymentMethodIdStr = request.getParameter("paymentMethodId");
            if (addressIdStr == null || paymentMethodIdStr == null || addressIdStr.isEmpty() || paymentMethodIdStr.isEmpty()) {
                throw new IllegalArgumentException("Please select a valid Shipping address and payment method.");
            }

            int addressId = Integer.parseInt(addressIdStr);
            int paymentMethodId = Integer.parseInt(paymentMethodIdStr);

            List<AddressModel> shippingAddresses = orderService.getUserAddresses(userId).stream()
                .filter(a -> "Shipping".equals(a.getAddressType()) && a.getAddressId() == addressId)
                .toList();
            if (shippingAddresses.isEmpty()) {
                throw new IllegalArgumentException("Selected address is not a valid Shipping address.");
            }

            double subtotal = 0.0;
            List<OrderProductModel> orderProducts = new ArrayList<>();
            for (CartModel item : cartItems) {
                ProductModel product = cartService.getProductById(item.getProductId());
                OrderProductModel orderProduct = new OrderProductModel();
                orderProduct.setProductId(product.getProductId());
                orderProduct.setQuantityOrdered(item.getCartQuantity());
                orderProduct.setUnitPrice(product.getProductPrice());
                orderProducts.add(orderProduct);
                subtotal += product.getProductPrice() * item.getCartQuantity();
            }
            double tax = subtotal * 0.10;
            double total = subtotal + tax;

            OrderModel order = new OrderModel();
            order.setUserId(userId);
            order.setOrderDate(new Date());
            order.setOrderStatus("Pending");
            order.setTotalAmount(total);
            order.setAddressId(addressId);
            order.setPaymentMethodId(paymentMethodId);
            order.setOrderPaymentStatus("Pending");
            order.setOrderProducts(orderProducts);

            int orderId = orderService.createOrder(order);
            if (orderId > 0) {
                SessionUtil.setAttribute(request, "orderMessage", "Order placed successfully.");
                response.sendRedirect(request.getContextPath() + "/orders");
            } else {
                SessionUtil.setAttribute(request, "checkoutMessage", "Failed to place order. Insufficient stock or other issue.");
                response.sendRedirect(request.getContextPath() + "/checkout");
            }
        } catch (Exception e) {
            e.printStackTrace();
            SessionUtil.setAttribute(request, "checkoutMessage", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/checkout");
        }
    }
}