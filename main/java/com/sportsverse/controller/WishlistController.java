package com.sportsverse.controller;

import com.sportsverse.model.ProductModel;
import com.sportsverse.service.WishlistService;
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

@WebServlet("/wishlist")
public class WishlistController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final WishlistService wishlistService = new WishlistService();
    private final RedirectionUtil redirectionUtil = new RedirectionUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int userId = (int) session.getAttribute("userId");
        try {
            List<ProductModel> wishlistProducts = wishlistService.getWishlistProducts(userId);
            req.setAttribute("wishlistProducts", wishlistProducts);
            redirectionUtil.redirectToPage(req, resp, "/WEB-INF/pages/wishlist.jsp");
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Error fetching wishlist products", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int userId = (int) session.getAttribute("userId");
        String action = req.getParameter("action");
        String redirectPage = req.getParameter("redirect") != null ? req.getParameter("redirect") : req.getContextPath() + "/wishlist";

        try {
            if ("add".equals(action)) {
                int productId = Integer.parseInt(req.getParameter("productId"));
                wishlistService.addToWishlist(userId, productId);
            } else if ("remove".equals(action)) {
                int wishlistId = Integer.parseInt(req.getParameter("wishlistId"));
                wishlistService.removeFromWishlist(wishlistId);
            } else {
                throw new ServletException("Invalid action");
            }
            resp.sendRedirect(redirectPage);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Error processing wishlist action", e);
        }
    }
}