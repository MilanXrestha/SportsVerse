package com.sportsverse.controller;

import com.sportsverse.model.CategoryModel;
import com.sportsverse.model.ProductModel;
import com.sportsverse.model.SubcategoryModel;
import com.sportsverse.service.HomeService;
import com.sportsverse.service.WishlistService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebServlet("/trending")
public class TrendingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final HomeService homeService = new HomeService();
	private final WishlistService wishlistService = new WishlistService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Map<CategoryModel, List<SubcategoryModel>> categoryMap = homeService.getAllCategoriesWithSubcategories();
			List<ProductModel> featuredProducts = homeService.getFeaturedProducts();
			List<ProductModel> wishlistedProducts = homeService.getMostWishlistedProducts();

			// Combine featured and wishlisted products, avoiding duplicates
			Set<Integer> productIds = new HashSet<>();
			List<ProductModel> trendingProducts = new ArrayList<>();

			// Add featured products first
			for (ProductModel product : featuredProducts) {
				if (trendingProducts.size() < 10 && productIds.add(product.getProductId())) {
					trendingProducts.add(product);
				}
			}

			// Add wishlisted products to fill remaining slots
			for (ProductModel product : wishlistedProducts) {
				if (trendingProducts.size() < 10 && productIds.add(product.getProductId())) {
					trendingProducts.add(product);
				}
			}

			Map<Integer, Integer> wishlistStatus = new HashMap<>();
			HttpSession session = req.getSession(false);
			if (session != null && session.getAttribute("userId") != null) {
				int userId = (int) session.getAttribute("userId");
				List<Integer> trendingProductIds = new ArrayList<>();
				for (ProductModel product : trendingProducts) {
					trendingProductIds.add(product.getProductId());
				}
				wishlistStatus = wishlistService.getWishlistStatus(userId, trendingProductIds);
			}

			req.setAttribute("categoryMap", categoryMap);
			req.setAttribute("trendingProducts", trendingProducts);
			req.setAttribute("wishlistStatus", wishlistStatus);
			req.setAttribute("activePage", "trending");
			req.getRequestDispatcher("/WEB-INF/pages/trending.jsp").forward(req, resp);
		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException("Error fetching trending data", e);
		}
	}
}