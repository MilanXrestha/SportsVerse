package com.sportsverse.controller;

import com.sportsverse.model.ProductModel;
import com.sportsverse.model.ReviewModel;
import com.sportsverse.service.ProductService;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet({"/product", "/product/review"})
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService productService = new ProductService();
    private final WishlistService wishlistService = new WishlistService();
    private final RedirectionUtil redirectionUtil = new RedirectionUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(req.getParameter("productId"));
            ProductModel product = productService.getProductById(productId);
            if (product == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }

            String brandName = productService.getBrandName(product.getBrandId());
            List<ReviewModel> reviews = productService.getReviewsByProductId(productId);
            int reviewCount = reviews.size();

            Map<Integer, Integer> wishlistStatus = new HashMap<>();
            HttpSession session = req.getSession(false);
            if (session != null && session.getAttribute("userId") != null) {
                int userId = (int) session.getAttribute("userId");
                wishlistStatus = wishlistService.getWishlistStatus(userId, Collections.singletonList(productId));
            }

            req.setAttribute("product", product);
            req.setAttribute("brandName", brandName);
            req.setAttribute("reviews", reviews);
            req.setAttribute("reviewCount", reviewCount);
            req.setAttribute("wishlistStatus", wishlistStatus);
            redirectionUtil.redirectToPage(req, resp, "/WEB-INF/pages/product.jsp");
            
        } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
            throw new ServletException("Error fetching product data", e);
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
        String path = req.getServletPath();

        if ("/product/review".equals(path)) {
            try {
                int productId = Integer.parseInt(req.getParameter("productId"));
                float rating = Float.parseFloat(req.getParameter("rating"));
                String reviewComment = req.getParameter("reviewComment");

                productService.addReview(userId, productId, rating, reviewComment);
                resp.sendRedirect(req.getContextPath() + "/product?productId=" + productId);
            } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
                throw new ServletException("Error submitting review", e);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }
}