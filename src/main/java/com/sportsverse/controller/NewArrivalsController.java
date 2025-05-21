package com.sportsverse.controller;

import com.sportsverse.model.CategoryModel;
import com.sportsverse.model.ProductModel;
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

@WebServlet("/newarrivals")
public class NewArrivalsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final RedirectionUtil redirectionUtil = new RedirectionUtil();
	private final HomeService homeService = new HomeService();
	private final WishlistService wishlistService = new WishlistService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Fetch new arrival products grouped by category
			Map<CategoryModel, List<ProductModel>> categoryProductMap = homeService.getNewArrivalProducts();
			Map<Integer, Integer> wishlistStatus = new HashMap<>();

			// Check for user session and fetch wishlist status
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

			// Set attributes for JSP
			req.setAttribute("categoryProductMap", categoryProductMap);
			req.setAttribute("wishlistStatus", wishlistStatus);
			req.setAttribute("activePage", "newarrivals");
			req.getRequestDispatcher("/WEB-INF/pages/newarrivals.jsp").forward(req, resp);

		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException("Error fetching new arrival products", e);
		}
	}
}