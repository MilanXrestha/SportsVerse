package com.sportsverse.controller;

import com.sportsverse.model.OrderModel;
import com.sportsverse.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/orders")
public class OrdersController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            int userId = (int) session.getAttribute("userId");
            List<OrderModel> orders = orderService.getUserOrders(userId);
            req.setAttribute("orders", orders);
            req.setAttribute("activePage", "orders");
            req.getRequestDispatcher("/WEB-INF/pages/orders.jsp").forward(req, resp);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Error fetching orders", e);
        }
    }
}