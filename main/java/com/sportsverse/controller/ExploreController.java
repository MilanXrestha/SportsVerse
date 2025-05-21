package com.sportsverse.controller;

import com.sportsverse.model.CategoryModel;
import com.sportsverse.model.ProductModel;
import com.sportsverse.model.SubcategoryModel;
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

@WebServlet("/explore")
public class ExploreController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final RedirectionUtil redirectionUtil = new RedirectionUtil();
	private final HomeService homeService = new HomeService();
	private final WishlistService wishlistService = new WishlistService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Fetch categories with their subcategories for header navigation
			Map<CategoryModel, List<SubcategoryModel>> categoryMap = homeService.getAllCategoriesWithSubcategories();
			Map<CategoryModel, List<ProductModel>> categoryProductMap = null;
			Map<Integer, Integer> wishlistStatus = new HashMap<>();
			String categoryName = null;
			String subcategoryName = null;

			// Retrieve query parameters
			String categoryIdParam = req.getParameter("categoryId");
			String subcategoryIdParam = req.getParameter("subcategoryId");
			String searchQuery = req.getParameter("query");

			// Handle search query if present
			if (searchQuery != null && !searchQuery.isEmpty()) {
				categoryProductMap = homeService.searchProducts(searchQuery);
			}
			// Handle category or subcategory filtering
			else if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
				try {
					int categoryId = Integer.parseInt(categoryIdParam);
					if (subcategoryIdParam != null && !subcategoryIdParam.isEmpty()) {
						// Filter by subcategory
						int subcategoryId = Integer.parseInt(subcategoryIdParam);
						categoryProductMap = homeService.getProductsBySubcategory(categoryId, subcategoryId);
						// Extract subcategory name for display
						for (Map.Entry<CategoryModel, List<SubcategoryModel>> entry : categoryMap.entrySet()) {
							if (entry.getKey().getCategoryId() == categoryId) {
								categoryName = entry.getKey().getCategoryName();
								for (SubcategoryModel sub : entry.getValue()) {
									if (sub.getSubcategoryId() == subcategoryId) {
										subcategoryName = sub.getSubcategoryName();
										break;
									}
								}
								break;
							}
						}
					} else {
						// Filter by category
						categoryProductMap = homeService.getProductsByCategory(categoryId);
						// Extract category name for display
						if (!categoryProductMap.isEmpty()) {
							categoryName = categoryProductMap.keySet().iterator().next().getCategoryName();
						}
					}
				} catch (NumberFormatException e) {
					// Handle invalid category or subcategory ID
					req.setAttribute("errorMessage", "Invalid category or subcategory ID");
					categoryProductMap = new HashMap<>();
				}
			} else {
				// Default: fetch all products
				categoryProductMap = homeService.getCategoriesWithProducts();
			}

			// Fetch wishlist status for logged-in user
			HttpSession session = req.getSession(false);
			if (session != null && session.getAttribute("userId") != null) {
				int userId = (int) session.getAttribute("userId");
				List<Integer> productIds = new ArrayList<>();
				for (List<ProductModel> products : categoryProductMap.values()) {
					for (ProductModel product : products) {
						productIds.add(product.getProductId());
					}
				}
				wishlistStatus = wishlistService.getWishlistStatus(userId, productIds);
			}

			// Set attributes for explore.jsp
			req.setAttribute("categoryMap", categoryMap);
			req.setAttribute("categoryProductMap", categoryProductMap);
			req.setAttribute("wishlistStatus", wishlistStatus);
			req.setAttribute("categoryName", categoryName);
			req.setAttribute("subcategoryName", subcategoryName);
			req.setAttribute("searchQuery", searchQuery);
			req.setAttribute("activePage", "explore");
			req.getRequestDispatcher("/WEB-INF/pages/explore.jsp").forward(req, resp);

		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException("Error fetching products", e);
		}
	}
}