package com.sportsverse.controller;

import com.sportsverse.model.CategoryModel;
import com.sportsverse.model.ProductModel;
import com.sportsverse.model.SubcategoryModel;
import com.sportsverse.model.SliderModel;
import com.sportsverse.service.HomeService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final RedirectionUtil redirectionUtil = new RedirectionUtil();
    private final HomeService homeService = new HomeService();
    private final WishlistService wishlistService = new WishlistService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Map<CategoryModel, List<SubcategoryModel>> categoryMap = homeService.getAllCategoriesWithSubcategories();
            List<SliderModel> sliders = homeService.getActiveSliders();
            List<ProductModel> featuredProducts = homeService.getFeaturedProducts();
            List<ProductModel> adidasProducts = homeService.getAdidasProducts();
            Map<Integer, Integer> wishlistStatus = new HashMap<>();

            HttpSession session = req.getSession(false);
            if (session != null && session.getAttribute("userId") != null) {
                int userId = (int) session.getAttribute("userId");
                List<Integer> productIds = new ArrayList<>();
                for (ProductModel product : featuredProducts) {
                    productIds.add(product.getProductId());
                }
                for (ProductModel product : adidasProducts) {
                    productIds.add(product.getProductId());
                }
                wishlistStatus = wishlistService.getWishlistStatus(userId, productIds);
            }

            req.setAttribute("categoryMap", categoryMap);
            req.setAttribute("sliders", sliders);
            req.setAttribute("featuredProducts", featuredProducts);
            req.setAttribute("adidasProducts", adidasProducts);
            req.setAttribute("wishlistStatus", wishlistStatus);
            
            
            req.setAttribute("activePage", "home");
            req.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(req, resp);
            
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Error fetching data", e);
        }
    }
}