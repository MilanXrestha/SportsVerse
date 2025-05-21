package com.sportsverse.controller;

import com.sportsverse.model.CartModel;
import com.sportsverse.model.ProductModel;
import com.sportsverse.service.CartService;
import com.sportsverse.util.RedirectionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final RedirectionUtil redirectionUtil = new RedirectionUtil();
    private final CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            int userId = (int) session.getAttribute("userId");
            List<CartModel> cartItems = cartService.getCartItems(userId);
            List<ProductModel> cartProducts = cartService.getCartProducts(cartItems);
            req.setAttribute("cartItems", cartItems);
            req.setAttribute("cartProducts", cartProducts);
            req.setAttribute("activePage", "cart");
            req.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(req, resp);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Error fetching cart data", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            int userId = (int) session.getAttribute("userId");
            String action = req.getParameter("action");

            if ("add".equals(action)) {
                int productId = Integer.parseInt(req.getParameter("productId"));
                boolean added = cartService.addToCart(userId, productId, 1);
                session.setAttribute("cartMessage", added ? "Product added to cart." : "Unable to add to cart. Product may be out of stock.");
            } else if ("remove".equals(action)) {
                int cartId = Integer.parseInt(req.getParameter("cartId"));
                boolean removed = cartService.removeFromCart(cartId);
                session.setAttribute("cartMessage", removed ? "Item removed from cart." : "Unable to remove item from cart.");
            } else if ("update".equals(action)) {
                int cartId = Integer.parseInt(req.getParameter("cartId"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));
                boolean updated = cartService.updateCartQuantity(cartId, quantity);
                session.setAttribute("cartMessage", updated ? "Quantity updated successfully." : "Unable to update quantity. Check stock availability.");
            } else if ("clear".equals(action)) {
                boolean cleared = cartService.clearCart(userId);
                session.setAttribute("cartMessage", cleared ? "Cart cleared successfully." : "Unable to clear cart.");
            }

            resp.sendRedirect(req.getContextPath() + "/cart");
        } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
            HttpSession session = req.getSession();
            session.setAttribute("cartMessage", "An error occurred: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}