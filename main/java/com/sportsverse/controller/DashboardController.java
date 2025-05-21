package com.sportsverse.controller;

import com.sportsverse.util.Crud;
import com.sportsverse.service.DashboardService;
import com.sportsverse.util.RedirectionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DashboardService dashboardService;
	private RedirectionUtil redirectionUtil;

	@Override
	public void init() throws ServletException {
		this.dashboardService = new DashboardService();
		this.redirectionUtil = new RedirectionUtil();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		// Restore session attributes
		Object message = session.getAttribute("message");
		if (message != null) {
			req.setAttribute("message", message);
			session.removeAttribute("message");
		}

		Object activeSection = session.getAttribute("activeSection");
		if (activeSection != null) {
			req.setAttribute("activeSection", activeSection);
			session.removeAttribute("activeSection");
		}

		// Get dashboard data
		setDashboardAttributes(req);

		String action = req.getParameter("action");

		if (action == null || action.isEmpty()) {
			req.setAttribute("activeSection",
					req.getAttribute("activeSection") != null ? req.getAttribute("activeSection") : "dashboard");

			// Brand
		} else if ("editBrand".equals(action)) {
			Crud.prepareEditBrand(req);
			req.setAttribute("activeSection", "add-brand");

			// Product
		} else if ("editProduct".equals(action)) {
			Crud.prepareEditProduct(req);
			req.setAttribute("activeSection", "add-product");

			// Category
		} else if ("editCategory".equals(action)) {
			Crud.prepareEditCategory(req);
			req.setAttribute("activeSection", "add-category");

			// SubCategory
		} else if ("editSubcategory".equals(action)) {
			Crud.prepareEditSubcategory(req);
			req.setAttribute("activeSection", "add-subcategory");

			// Slider
		} else if ("editSlider".equals(action)) {
			Crud.prepareEditSlider(req);
			req.setAttribute("activeSection", "add-slider");

			// Order
		} else if ("editOrder".equals(action)) {
			Crud.prepareEditOrder(req);
			req.setAttribute("activeSection", "edit-order");

			// Payment Method
		} else if ("editPaymentMethod".equals(action)) {
			Crud.prepareEditPaymentMethod(req);
			req.setAttribute("activeSection", "add-payment-method");

			// Payment
		} else if ("editPayment".equals(action)) {
			Crud.prepareEditPayment(req);
			req.setAttribute("activeSection", "edit-payment");

			// Cancel Edit
		} else if ("cancelEdit".equals(action)) {
			resp.sendRedirect(req.getContextPath() + "/dashboard");
			return;

			// Search Product
		} else if ("searchProducts".equals(action)) {
			req.setAttribute("activeSection", "products");

		} else {
			req.setAttribute("message", "Invalid action requested");
			req.setAttribute("activeSection", "dashboard");
		}

		redirectionUtil.redirectToPage(req, resp, "WEB-INF/pages/dashboard.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String action = req.getParameter("action");

		if (action == null) {
			session.setAttribute("message", "No action specified");
			session.setAttribute("activeSection", "dashboard");
			resp.sendRedirect(req.getContextPath() + "/dashboard");
			return;
		}

		switch (action) {
		// BRAND
		case "addBrand":
			Crud.handleAddBrand(req, session);
			session.setAttribute("activeSection", "brands");
			break;
		case "editBrand":
			Crud.handleEditBrand(req, session);
			session.setAttribute("activeSection", "brands");
			break;
		case "deleteBrand":
			Crud.handleDeleteBrand(req, session);
			session.setAttribute("activeSection", "brands");
			break;

		// PRODUCT
		case "addProduct":
			Crud.handleAddProduct(req, session);
			session.setAttribute("activeSection", "products");
			break;
		case "editProduct":
			Crud.handleEditProduct(req, session);
			session.setAttribute("activeSection", "products");
			break;
		case "deleteProduct":
			Crud.handleDeleteProduct(req, session);
			session.setAttribute("activeSection", "products");
			break;

		// CATEGORY
		case "addCategory":
			Crud.handleAddCategory(req, session);
			session.setAttribute("activeSection", "categories");
			break;
		case "editCategory":
			Crud.handleEditCategory(req, session);
			session.setAttribute("activeSection", "categories");
			break;
		case "deleteCategory":
			Crud.handleDeleteCategory(req, session);
			session.setAttribute("activeSection", "categories");
			break;

		// SUBCATEGORY
		case "addSubcategory":
			Crud.handleAddSubcategory(req, session);
			session.setAttribute("activeSection", "subcategories");
			break;
		case "editSubcategory":
			Crud.handleEditSubcategory(req, session);
			session.setAttribute("activeSection", "subcategories");
			break;
		case "deleteSubcategory":
			Crud.handleDeleteSubcategory(req, session);
			session.setAttribute("activeSection", "subcategories");
			break;

		// SLIDER
		case "addSlider":
			Crud.handleAddSlider(req, session);
			session.setAttribute("activeSection", "sliders");
			break;
		case "editSlider":
			Crud.handleEditSlider(req, session);
			session.setAttribute("activeSection", "sliders");
			break;
		case "deleteSlider":
			Crud.handleDeleteSlider(req, session);
			session.setAttribute("activeSection", "sliders");
			break;

		// ORDER
		case "editOrder":
			Crud.handleEditOrder(req, session);
			session.setAttribute("activeSection", "orders");
			break;
		case "deleteOrder":
			Crud.handleDeleteOrder(req, session);
			session.setAttribute("activeSection", "orders");
			break;

		// PAYMENT METHOD
		case "addPaymentMethod":
			Crud.handleAddPaymentMethod(req, session);
			session.setAttribute("activeSection", "payment-methods");
			break;
		case "editPaymentMethod":
			Crud.handleEditPaymentMethod(req, session);
			session.setAttribute("activeSection", "payment-methods");
			break;
		case "deletePaymentMethod":
			Crud.handleDeletePaymentMethod(req, session);
			session.setAttribute("activeSection", "payment-methods");
			break;

		// PAYMENT
		case "editPayment":
			Crud.handleEditPayment(req, session);
			session.setAttribute("activeSection", "payments");
			break;
		case "deletePayment":
			Crud.handleDeletePayment(req, session);
			session.setAttribute("activeSection", "payments");
			break;

		// Review
		case "verifyReview":
			Crud.handleVerifyReview(req, session);
			session.setAttribute("activeSection", "reviews");
			break;
		case "deleteReview":
			Crud.handleDeleteReview(req, session);
			session.setAttribute("activeSection", "reviews");
			break;

		// REPORT
		case "generateReport":
			Crud.handleGenerateReport(req, session);
			session.setAttribute("activeSection", "generate-report");
			break;

		default:
			session.setAttribute("message", "Invalid action requested");
			session.setAttribute("activeSection", "dashboard");
		}

		resp.sendRedirect(req.getContextPath() + "/dashboard");
	}

	private void setDashboardAttributes(HttpServletRequest req) {
		req.setAttribute("totalUsers", dashboardService.getTotalUsers());
		req.setAttribute("totalOrders", dashboardService.getTotalOrders());
		req.setAttribute("lowStockProducts", dashboardService.getLowStockProducts());
		req.setAttribute("totalBrands", dashboardService.getTotalBrands());
		req.setAttribute("totalCategories", dashboardService.getTotalCategories());
		req.setAttribute("totalProducts", dashboardService.getTotalProducts());
		req.setAttribute("featuredProducts", dashboardService.getFeaturedProducts());
		req.setAttribute("totalRevenues", dashboardService.getTotalRevenues());
		req.setAttribute("recentUsers", dashboardService.getRecentUsers());

		req.setAttribute("users", dashboardService.getAllUsers());
		req.setAttribute("addresses", dashboardService.getAllAddresses());
		req.setAttribute("brands", dashboardService.getAllBrands());



		// Search for Product
		String searchQuery = req.getParameter("searchQuery");
		req.setAttribute("products",
				searchQuery != null && !searchQuery.trim().isEmpty() ? dashboardService.searchProducts(searchQuery)
						: dashboardService.getAllProducts());
		req.setAttribute("searchQuery", searchQuery);

		req.setAttribute("subcategories", dashboardService.getAllSubcategories());
		req.setAttribute("categories", dashboardService.getAllCategories());
		req.setAttribute("sliders", dashboardService.getAllSliders());
		req.setAttribute("orders", dashboardService.getAllOrders());
		req.setAttribute("carts", dashboardService.getAllCarts());
		req.setAttribute("paymentMethods", dashboardService.getAllPaymentMethods());
		req.setAttribute("payments", dashboardService.getAllPayments());
		req.setAttribute("reviews", dashboardService.getAllReviews());
	}
}