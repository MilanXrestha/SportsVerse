package com.sportsverse.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import com.sportsverse.model.BrandModel;
import com.sportsverse.model.CategoryModel;
import com.sportsverse.model.OrderModel;
import com.sportsverse.model.PaymentMethodModel;
import com.sportsverse.model.PaymentModel;
import com.sportsverse.model.ProductModel;
import com.sportsverse.model.ReportModel;
import com.sportsverse.model.ReviewModel;
import com.sportsverse.model.SliderModel;
import com.sportsverse.model.SubcategoryModel;
import com.sportsverse.service.DashboardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.text.ParseException;

import java.util.Date;

public class Crud {

	private static final DashboardService dashboardService = new DashboardService();

	// Add Brand
	public static void handleAddBrand(HttpServletRequest req, HttpSession session) {
		String brandName = req.getParameter("brandName");

		if (brandName == null || brandName.trim().isEmpty()) {
			session.setAttribute("message", "Error: Brand name is required");
			return;
		}

		// Check for duplicate brand name (pass a dummy ID like -1 for brand creation)
		if (dashboardService.isBrandNameExists(brandName.trim(), -1)) {
			session.setAttribute("message", "Error: Brand name already exists");
			return;
		}

		BrandModel brand = new BrandModel();
		brand.setBrandName(brandName.trim());
		boolean success = dashboardService.addBrand(brand);

		session.setAttribute("message", success ? "Brand added successfully" : "Error: Failed to add brand");
	}

	// Edit Brand
	public static void handleEditBrand(HttpServletRequest req, HttpSession session) {
		String id = req.getParameter("brandId");
		String brandName = req.getParameter("brandName");

		try {
			int brandId = Integer.parseInt(id);

			if (brandName == null || brandName.trim().isEmpty()) {
				session.setAttribute("message", "Error: Brand name is required");
				return;
			}

			// Check for duplicate brand name excluding current ID
			if (dashboardService.isBrandNameExists(brandName.trim(), brandId)) {
				session.setAttribute("message", "Error: Brand name already exists");
				return;
			}

			BrandModel brand = new BrandModel();
			brand.setBrandId(brandId);
			brand.setBrandName(brandName.trim());

			boolean success = dashboardService.updateBrand(brand);
			session.setAttribute("message", success ? "Brand updated successfully" : "Error: Failed to update brand");

		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid brand ID");
		}
	}

	// Delete Brand
	public static void handleDeleteBrand(HttpServletRequest req, HttpSession session) {
		String id = req.getParameter("id");

		try {
			int brandId = Integer.parseInt(id);
			boolean success = dashboardService.deleteBrand(brandId);
			session.setAttribute("message", success ? "Brand deleted successfully" : "Error: Failed to delete brand");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid brand ID");
		}
	}

	public static void prepareEditBrand(HttpServletRequest req) {
		String id = req.getParameter("id");

		try {
			int brandId = Integer.parseInt(id);
			BrandModel brand = dashboardService.getBrandById(brandId);

			if (brand != null) {
				req.setAttribute("brand", brand);
				req.setAttribute("activeSection", "edit-brand");
			} else {
				req.setAttribute("message", "Error: Brand not found");
				req.setAttribute("activeSection", "brands");
			}
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Error: Invalid brand ID");
			req.setAttribute("activeSection", "brands");
		}
	}

	// Add Product
	public static void handleAddProduct(HttpServletRequest req, HttpSession session) {
		String productName = req.getParameter("productName");
		String subcategoryId = req.getParameter("subcategoryId");
		String brandId = req.getParameter("brandId");
		String gender = req.getParameter("gender");
		String productMaterial = req.getParameter("productMaterial");
		String productDescription = req.getParameter("productDescription");
		String productPrice = req.getParameter("productPrice");
		String productSize = req.getParameter("productSize");
		String stockQuantity = req.getParameter("stockQuantity");
		String mainImageUrl = req.getParameter("mainImageUrl");
		String productRating = req.getParameter("productRating");
		String isFeatured = req.getParameter("isFeatured");

		// Validate required fields
		if (productName == null || productName.trim().isEmpty() || subcategoryId == null
				|| subcategoryId.trim().isEmpty() || brandId == null || brandId.trim().isEmpty() || gender == null
				|| gender.trim().isEmpty() || productPrice == null || productPrice.trim().isEmpty()
				|| stockQuantity == null || stockQuantity.trim().isEmpty()) {
			session.setAttribute("message", "Error: All required fields must be filled");
			return;
		}

		// Check for duplicate product name
		if (dashboardService.isProductNameExists(productName.trim(), -1)) {
			session.setAttribute("message", "Error: Product name already exists");
			return;
		}

		try {
			ProductModel product = new ProductModel();
			product.setProductName(productName.trim());
			product.setSubcategoryId(Integer.parseInt(subcategoryId));
			product.setBrandId(Integer.parseInt(brandId));
			product.setGender(gender.trim());
			product.setProductMaterial(productMaterial != null ? productMaterial.trim() : null);
			product.setProductDescription(productDescription != null ? productDescription.trim() : null);
			product.setProductPrice(Double.parseDouble(productPrice));
			product.setProductSize(productSize != null ? productSize.trim() : null);
			product.setStockQuantity(Integer.parseInt(stockQuantity));
			product.setMainImageUrl(mainImageUrl != null ? mainImageUrl.trim() : null);
			product.setProductRating(
					productRating != null && !productRating.trim().isEmpty() ? Double.parseDouble(productRating) : 0.0);
			product.setFeatured("true".equals(isFeatured));

			boolean success = dashboardService.addProduct(product);
			session.setAttribute("message", success ? "Product added successfully" : "Error: Failed to add product");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid number format in input");
		}
	}

	// Edit Product
	public static void handleEditProduct(HttpServletRequest req, HttpSession session) {
		String productId = req.getParameter("productId");
		String productName = req.getParameter("productName");
		String subcategoryId = req.getParameter("subcategoryId");
		String brandId = req.getParameter("brandId");
		String gender = req.getParameter("gender");
		String productMaterial = req.getParameter("productMaterial");
		String productDescription = req.getParameter("productDescription");
		String productPrice = req.getParameter("productPrice");
		String productSize = req.getParameter("productSize");
		String stockQuantity = req.getParameter("stockQuantity");
		String mainImageUrl = req.getParameter("mainImageUrl");
		String productRating = req.getParameter("productRating");
		String isFeatured = req.getParameter("isFeatured");

		// Validate required fields
		if (productId == null || productId.trim().isEmpty() || productName == null || productName.trim().isEmpty()
				|| subcategoryId == null || subcategoryId.trim().isEmpty() || brandId == null
				|| brandId.trim().isEmpty() || gender == null || gender.trim().isEmpty() || productPrice == null
				|| productPrice.trim().isEmpty() || stockQuantity == null || stockQuantity.trim().isEmpty()) {
			session.setAttribute("message", "Error: All required fields must be filled");
			return;
		}

		try {
			int id = Integer.parseInt(productId);

			// Check for duplicate product name excluding current ID
			if (dashboardService.isProductNameExists(productName.trim(), id)) {
				session.setAttribute("message", "Error: Product name already exists");
				return;
			}

			ProductModel product = new ProductModel();
			product.setProductId(id);
			product.setProductName(productName.trim());
			product.setSubcategoryId(Integer.parseInt(subcategoryId));
			product.setBrandId(Integer.parseInt(brandId));
			product.setGender(gender.trim());
			product.setProductMaterial(productMaterial != null ? productMaterial.trim() : null);
			product.setProductDescription(productDescription != null ? productDescription.trim() : null);
			product.setProductPrice(Double.parseDouble(productPrice));
			product.setProductSize(productSize != null ? productSize.trim() : null);
			product.setStockQuantity(Integer.parseInt(stockQuantity));
			product.setMainImageUrl(mainImageUrl != null ? mainImageUrl.trim() : null);
			product.setProductRating(
					productRating != null && !productRating.trim().isEmpty() ? Double.parseDouble(productRating) : 0.0);
			product.setFeatured("true".equals(isFeatured));

			boolean success = dashboardService.updateProduct(product);
			session.setAttribute("message",
					success ? "Product updated successfully" : "Error: Failed to update product");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid number format in input");
		}
	}

	// Delete Product
	public static void handleDeleteProduct(HttpServletRequest req, HttpSession session) {
		String id = req.getParameter("id");

		try {
			int productId = Integer.parseInt(id);
			boolean success = dashboardService.deleteProduct(productId);
			session.setAttribute("message",
					success ? "Product deleted successfully" : "Error: Failed to delete product");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid product ID");
		}
	}

	// Prepare Edit Product
	public static void prepareEditProduct(HttpServletRequest req) {
		String id = req.getParameter("id");

		try {
			int productId = Integer.parseInt(id);
			ProductModel product = dashboardService.getProductById(productId);

			if (product != null) {
				req.setAttribute("product", product);
				req.setAttribute("activeSection", "add-product");
			} else {
				req.setAttribute("message", "Error: Product not found");
				req.setAttribute("activeSection", "products");
			}
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Error: Invalid product ID");
			req.setAttribute("activeSection", "products");
		}
	}

	// Add Category
	public static void handleAddCategory(HttpServletRequest req, HttpSession session) {
		String categoryName = req.getParameter("categoryName");
		String categoryImage = req.getParameter("categoryImage");

		if (categoryName == null || categoryName.trim().isEmpty()) {
			session.setAttribute("message", "Error: Category name is required");
			return;
		}

		// Check for duplicate category name (pass -1 for new category)
		if (dashboardService.isCategoryNameExists(categoryName.trim(), -1)) {
			session.setAttribute("message", "Error: Category name already exists");
			return;
		}

		CategoryModel category = new CategoryModel();
		category.setCategoryName(categoryName.trim());
		category.setCategoryImage(
				categoryImage != null && !categoryImage.trim().isEmpty() ? categoryImage.trim() : null);
		boolean success = dashboardService.addCategory(category);

		session.setAttribute("message", success ? "Category added successfully" : "Error: Failed to add category");
	}

	// Edit Category
	public static void handleEditCategory(HttpServletRequest req, HttpSession session) {
		String id = req.getParameter("categoryId");
		String categoryName = req.getParameter("categoryName");
		String categoryImage = req.getParameter("categoryImage");

		try {
			int categoryId = Integer.parseInt(id);

			if (categoryName == null || categoryName.trim().isEmpty()) {
				session.setAttribute("message", "Error: Category name is required");
				return;
			}

			// Check for duplicate category name excluding current ID
			if (dashboardService.isCategoryNameExists(categoryName.trim(), categoryId)) {
				session.setAttribute("message", "Error: Category name already exists");
				return;
			}

			CategoryModel category = new CategoryModel();
			category.setCategoryId(categoryId);
			category.setCategoryName(categoryName.trim());
			category.setCategoryImage(
					categoryImage != null && !categoryImage.trim().isEmpty() ? categoryImage.trim() : null);
			boolean success = dashboardService.updateCategory(category);
			session.setAttribute("message",
					success ? "Category updated successfully" : "Error: Failed to update category");

		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid category ID");
		}
	}

	// Delete Category
	public static void handleDeleteCategory(HttpServletRequest req, HttpSession session) {
		String id = req.getParameter("id");

		try {
			int categoryId = Integer.parseInt(id);
			boolean success = dashboardService.deleteCategory(categoryId);
			session.setAttribute("message",
					success ? "Category deleted successfully" : "Error: Failed to delete category");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid category ID");
		}
	}

	// Prepare Edit Category
	public static void prepareEditCategory(HttpServletRequest req) {
		String id = req.getParameter("id");

		try {
			int categoryId = Integer.parseInt(id);
			CategoryModel category = dashboardService.getCategoryById(categoryId);

			if (category != null) {
				req.setAttribute("category", category);
				req.setAttribute("activeSection", "add-category");
			} else {
				req.setAttribute("message", "Error: Category not found");
				req.setAttribute("activeSection", "categories");
			}
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Error: Invalid category ID");
			req.setAttribute("activeSection", "categories");
		}
	}

	// Add Subcategory
	public static void handleAddSubcategory(HttpServletRequest req, HttpSession session) {
		String categoryId = req.getParameter("categoryId");
		String subcategoryName = req.getParameter("subcategoryName");

		if (categoryId == null || categoryId.trim().isEmpty() || subcategoryName == null
				|| subcategoryName.trim().isEmpty()) {
			session.setAttribute("message", "Error: Category and subcategory name are required");
			return;
		}

		try {
			int catId = Integer.parseInt(categoryId);

			// Check for duplicate subcategory name within the same category
			if (dashboardService.isSubcategoryNameExists(subcategoryName.trim(), catId, -1)) {
				session.setAttribute("message", "Error: Subcategory name already exists in this category");
				return;
			}

			SubcategoryModel subcategory = new SubcategoryModel();
			subcategory.setCategoryId(catId);
			subcategory.setSubcategoryName(subcategoryName.trim());

			boolean success = dashboardService.addSubcategory(subcategory);
			session.setAttribute("message",
					success ? "Subcategory added successfully" : "Error: Failed to add subcategory");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid category ID");
		}
	}

	// Edit Subcategory
	public static void handleEditSubcategory(HttpServletRequest req, HttpSession session) {
		String subcategoryId = req.getParameter("subcategoryId");
		String categoryId = req.getParameter("categoryId");
		String subcategoryName = req.getParameter("subcategoryName");

		if (subcategoryId == null || subcategoryId.trim().isEmpty() || categoryId == null || categoryId.trim().isEmpty()
				|| subcategoryName == null || subcategoryName.trim().isEmpty()) {
			session.setAttribute("message", "Error: All required fields must be filled");
			return;
		}

		try {
			int subcatId = Integer.parseInt(subcategoryId);
			int catId = Integer.parseInt(categoryId);

			// Check for duplicate subcategory name excluding current ID
			if (dashboardService.isSubcategoryNameExists(subcategoryName.trim(), catId, subcatId)) {
				session.setAttribute("message", "Error: Subcategory name already exists in this category");
				return;
			}

			SubcategoryModel subcategory = new SubcategoryModel();
			subcategory.setSubcategoryId(subcatId);
			subcategory.setCategoryId(catId);
			subcategory.setSubcategoryName(subcategoryName.trim());

			boolean success = dashboardService.updateSubcategory(subcategory);
			session.setAttribute("message",
					success ? "Subcategory updated successfully" : "Error: Failed to update subcategory");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid ID format");
		}
	}

	// Delete Subcategory
	public static void handleDeleteSubcategory(HttpServletRequest req, HttpSession session) {
		String id = req.getParameter("id");

		try {
			int subcategoryId = Integer.parseInt(id);
			if (dashboardService.hasProducts(subcategoryId)) {
				session.setAttribute("message", "Error: Cannot delete subcategory with associated products");
				return;
			}
			boolean success = dashboardService.deleteSubcategory(subcategoryId);
			session.setAttribute("message",
					success ? "Subcategory deleted successfully" : "Error: Failed to delete subcategory");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid subcategory ID");
		}
	}

	// Prepare Edit Subcategory
	public static void prepareEditSubcategory(HttpServletRequest req) {
		String id = req.getParameter("id");

		try {
			int subcategoryId = Integer.parseInt(id);
			SubcategoryModel subcategory = dashboardService.getSubcategoryById(subcategoryId);

			if (subcategory != null) {
				req.setAttribute("subcategory", subcategory);
				req.setAttribute("activeSection", "add-subcategory");
			} else {
				req.setAttribute("message", "Error: Subcategory not found");
				req.setAttribute("activeSection", "subcategories");
			}
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Error: Invalid subcategory ID");
			req.setAttribute("activeSection", "subcategories");
		}
	}

	/**
     * Handles adding a new slider from the dashboard form.
     * 
     * @param req     The HTTP request containing form parameters
     * @param session The HTTP session for storing messages
     */
    public static void handleAddSlider(HttpServletRequest req, HttpSession session) {
        String sliderImageUrl = req.getParameter("sliderImageUrl");
        String sliderText = req.getParameter("sliderText");
        String sliderSubText = req.getParameter("sliderSubText");
        String sliderOrder = req.getParameter("sliderOrder");
        String isActive = req.getParameter("isActive");
        String sliderLink = req.getParameter("sliderLink");

        // Validate required fields
        if (sliderImageUrl == null || sliderImageUrl.trim().isEmpty() || 
            sliderText == null || sliderText.trim().isEmpty() || 
            sliderOrder == null || sliderOrder.trim().isEmpty()) {
            session.setAttribute("message", "Error: Image URL, text, and order are required");
            return;
        }

        // Validate sliderLink (optional, must be a relative path if provided)
        if (sliderLink != null && !sliderLink.trim().isEmpty() && !sliderLink.matches("^/[^\\s]*$")) {
            session.setAttribute("message", "Error: CTA Link must be a valid relative path (e.g., /products?categoryId=1)");
            return;
        }

        try {
            int order = Integer.parseInt(sliderOrder);

            // Check for duplicate slider order
            if (dashboardService.isSliderOrderExists(order, -1)) {
                session.setAttribute("message", "Error: Slider order already exists");
                return;
            }

            SliderModel slider = new SliderModel();
            slider.setSliderImageUrl(sliderImageUrl.trim());
            slider.setSliderText(sliderText.trim());
            slider.setSliderSubText(sliderSubText != null ? sliderSubText.trim() : null);
            slider.setSliderOrder(order);
            slider.setActive("true".equals(isActive));
            slider.setSliderLink(sliderLink != null && !sliderLink.trim().isEmpty() ? sliderLink.trim() : null);

            boolean success = dashboardService.addSlider(slider);
            session.setAttribute("message", success ? "Slider added successfully" : "Error: Failed to add slider");
        } catch (NumberFormatException e) {
            session.setAttribute("message", "Error: Invalid slider order format");
        }
    }

    /**
     * Handles updating an existing slider from the dashboard form.
     * 
     * @param req     The HTTP request containing form parameters
     * @param session The HTTP session for storing messages
     */
    public static void handleEditSlider(HttpServletRequest req, HttpSession session) {
        String sliderId = req.getParameter("sliderId");
        String sliderImageUrl = req.getParameter("sliderImageUrl");
        String sliderText = req.getParameter("sliderText");
        String sliderSubText = req.getParameter("sliderSubText");
        String sliderOrder = req.getParameter("sliderOrder");
        String isActive = req.getParameter("isActive");
        String sliderLink = req.getParameter("sliderLink");

        // Validate required fields
        if (sliderId == null || sliderId.trim().isEmpty() || 
            sliderImageUrl == null || sliderImageUrl.trim().isEmpty() || 
            sliderText == null || sliderText.trim().isEmpty() || 
            sliderOrder == null || sliderOrder.trim().isEmpty()) {
            session.setAttribute("message", "Error: All required fields must be filled");
            return;
        }

        // Validate sliderLink (optional, must be a relative path if provided)
        if (sliderLink != null && !sliderLink.trim().isEmpty() && !sliderLink.matches("^/[^\\s]*$")) {
            session.setAttribute("message", "Error: CTA Link must be a valid relative path (e.g., /products?categoryId=1)");
            return;
        }

        try {
            int id = Integer.parseInt(sliderId);
            int order = Integer.parseInt(sliderOrder);

            // Check for duplicate slider order excluding current ID
            if (dashboardService.isSliderOrderExists(order, id)) {
                session.setAttribute("message", "Error: Slider order already exists");
                return;
            }

            SliderModel slider = new SliderModel();
            slider.setSliderId(id);
            slider.setSliderImageUrl(sliderImageUrl.trim());
            slider.setSliderText(sliderText.trim());
            slider.setSliderSubText(sliderSubText != null ? sliderSubText.trim() : null);
            slider.setSliderOrder(order);
            slider.setActive("true".equals(isActive));
            slider.setSliderLink(sliderLink != null && !sliderLink.trim().isEmpty() ? sliderLink.trim() : null);

            boolean success = dashboardService.updateSlider(slider);
            session.setAttribute("message", success ? "Slider updated successfully" : "Error: Failed to update slider");
        } catch (NumberFormatException e) {
            session.setAttribute("message", "Error: Invalid ID or order format");
        }
    }

    /**
     * Handles deleting a slider.
     * 
     * @param req     The HTTP request containing the slider ID
     * @param session The HTTP session for storing messages
     */
    public static void handleDeleteSlider(HttpServletRequest req, HttpSession session) {
        String id = req.getParameter("id");

        try {
            int sliderId = Integer.parseInt(id);
            boolean success = dashboardService.deleteSlider(sliderId);
            session.setAttribute("message", success ? "Slider deleted successfully" : "Error: Failed to delete slider");
        } catch (NumberFormatException e) {
            session.setAttribute("message", "Error: Invalid slider ID");
        }
    }

    /**
     * Prepares the edit slider form by fetching the slider data.
     * 
     * @param req The HTTP request containing the slider ID
     */
    public static void prepareEditSlider(HttpServletRequest req) {
        String id = req.getParameter("id");

        try {
            int sliderId = Integer.parseInt(id);
            SliderModel slider = dashboardService.getSliderById(sliderId);

            if (slider != null) {
                req.setAttribute("slider", slider);
                req.setAttribute("activeSection", "add-slider");
            } else {
                req.setAttribute("message", "Error: Slider not found");
                req.setAttribute("activeSection", "sliders");
            }
        } catch (NumberFormatException e) {
            req.setAttribute("message", "Error: Invalid slider ID");
            req.setAttribute("activeSection", "sliders");
        }
    }

	// Edit Order
	public static void handleEditOrder(HttpServletRequest req, HttpSession session) {
		String orderId = req.getParameter("orderId");
		String orderStatus = req.getParameter("orderStatus");
		String orderPaymentStatus = req.getParameter("orderPaymentStatus");

		// Validate required fields
		if (orderId == null || orderId.trim().isEmpty() || orderStatus == null || orderStatus.trim().isEmpty()
				|| orderPaymentStatus == null || orderPaymentStatus.trim().isEmpty()) {
			session.setAttribute("message", "Error: Delivery status and payment status are required");
			return;
		}

		// Validate allowed status values
		List<String> validOrderStatuses = Arrays.asList("Pending", "Processing", "Shipped", "Delivered", "Cancelled");
		List<String> validPaymentStatuses = Arrays.asList("Pending", "Completed", "Failed");
		if (!validOrderStatuses.contains(orderStatus) || !validPaymentStatuses.contains(orderPaymentStatus)) {
			session.setAttribute("message", "Error: Invalid status value");
			return;
		}

		try {
			int id = Integer.parseInt(orderId);

			OrderModel order = new OrderModel();
			order.setOrderId(id);
			order.setOrderStatus(orderStatus.trim());
			order.setOrderPaymentStatus(orderPaymentStatus.trim());

			boolean success = dashboardService.updateOrder(order);
			session.setAttribute("message", success ? "Order updated successfully" : "Error: Failed to update order");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid order ID");
		}
	}

	// Delete Order
	public static void handleDeleteOrder(HttpServletRequest req, HttpSession session) {
		String id = req.getParameter("id");

		try {
			int orderId = Integer.parseInt(id);
			if (dashboardService.hasPayments(orderId)) {
				session.setAttribute("message", "Error: Cannot delete order with associated payments");
				return;
			}
			boolean success = dashboardService.deleteOrder(orderId);
			session.setAttribute("message", success ? "Order deleted successfully" : "Error: Failed to delete order");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid order ID");
		}
	}

	// Prepare Edit Order
	public static void prepareEditOrder(HttpServletRequest req) {
		String id = req.getParameter("id");

		try {
			int orderId = Integer.parseInt(id);
			OrderModel order = dashboardService.getOrderById(orderId);

			if (order != null) {
				req.setAttribute("order", order);
				req.setAttribute("products", dashboardService.getAllProducts()); // Add products for name lookup
				req.setAttribute("activeSection", "edit-order");
			} else {
				req.setAttribute("message", "Error: Order not found");
				req.setAttribute("activeSection", "orders");
			}
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Error: Invalid order ID");
			req.setAttribute("activeSection", "orders");
		}
	}

	// Add Payment Method
	public static void handleAddPaymentMethod(HttpServletRequest req, HttpSession session) {
		String methodName = req.getParameter("methodName");

		// Validate required field
		if (methodName == null || methodName.trim().isEmpty()) {
			session.setAttribute("message", "Error: Method name is required");
			session.setAttribute("activeSection", "add-payment-method");
			return;
		}

		// Validate length
		if (methodName.trim().length() > 100) {
			session.setAttribute("message", "Error: Method name must be 100 characters or less");
			session.setAttribute("activeSection", "add-payment-method");
			return;
		}

		PaymentMethodModel method = new PaymentMethodModel();
		method.setMethodName(methodName.trim());

		// Check for duplicate via service
		if (dashboardService.isPaymentMethodNameExists(methodName.trim(), 0)) {
			session.setAttribute("message", "Error: Payment method name already exists");
			session.setAttribute("activeSection", "add-payment-method");
			return;
		}

		boolean success = dashboardService.addPaymentMethod(method);
		session.setAttribute("message",
				success ? "Payment method added successfully" : "Error: Failed to add payment method");
		session.setAttribute("activeSection", "payment-methods");
	}

	// Edit Payment Method
	public static void handleEditPaymentMethod(HttpServletRequest req, HttpSession session) {
		String paymentMethodId = req.getParameter("paymentMethodId");
		String methodName = req.getParameter("methodName");

		// Validate required fields
		if (paymentMethodId == null || paymentMethodId.trim().isEmpty() || methodName == null
				|| methodName.trim().isEmpty()) {
			session.setAttribute("message", "Error: Method name is required");
			session.setAttribute("activeSection", "add-payment-method");
			return;
		}

		// Validate length
		if (methodName.trim().length() > 100) {
			session.setAttribute("message", "Error: Method name must be 100 characters or less");
			session.setAttribute("activeSection", "add-payment-method");
			return;
		}

		try {
			int id = Integer.parseInt(paymentMethodId);

			PaymentMethodModel method = new PaymentMethodModel();
			method.setPaymentMethodId(id);
			method.setMethodName(methodName.trim());

			// Check for duplicate via service
			if (dashboardService.isPaymentMethodNameExists(methodName.trim(), id)) {
				session.setAttribute("message", "Error: Payment method name already exists");
				session.setAttribute("activeSection", "add-payment-method");
				return;
			}

			boolean success = dashboardService.updatePaymentMethod(method);
			session.setAttribute("message",
					success ? "Payment method updated successfully" : "Error: Failed to update payment method");
			session.setAttribute("activeSection", "payment-methods");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid payment method ID");
			session.setAttribute("activeSection", "add-payment-method");
		}
	}

	// Delete Payment Method
	public static void handleDeletePaymentMethod(HttpServletRequest req, HttpSession session) {
		String id = req.getParameter("id");

		try {
			int paymentMethodId = Integer.parseInt(id);
			if (dashboardService.isPaymentMethodUsed(paymentMethodId)) {
				session.setAttribute("message", "Error: Cannot delete payment method used in orders or payments");
				session.setAttribute("activeSection", "payment-methods");
				return;
			}
			boolean success = dashboardService.deletePaymentMethod(paymentMethodId);
			session.setAttribute("message",
					success ? "Payment method deleted successfully" : "Error: Failed to delete payment method");
			session.setAttribute("activeSection", "payment-methods");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid payment method ID");
			session.setAttribute("activeSection", "payment-methods");
		}
	}

	// Prepare Edit Payment Method
	public static void prepareEditPaymentMethod(HttpServletRequest req) {
		String id = req.getParameter("id");

		try {
			int paymentMethodId = Integer.parseInt(id);
			PaymentMethodModel method = dashboardService.getPaymentMethodById(paymentMethodId);

			if (method != null) {
				req.setAttribute("paymentMethod", method);
				req.setAttribute("activeSection", "add-payment-method");
			} else {
				req.setAttribute("message", "Error: Payment method not found");
				req.setAttribute("activeSection", "payment-methods");
			}
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Error: Invalid payment method ID");
			req.setAttribute("activeSection", "payment-methods");
		}
	}

	// Edit Payment
	public static void handleEditPayment(HttpServletRequest req, HttpSession session) {
		String paymentId = req.getParameter("paymentId");
		String paymentStatus = req.getParameter("paymentStatus");

		// Validate required fields
		if (paymentId == null || paymentId.trim().isEmpty() || paymentStatus == null
				|| paymentStatus.trim().isEmpty()) {
			session.setAttribute("message", "Error: Payment status is required");
			session.setAttribute("activeSection", "edit-payment");
			return;
		}

		// Validate payment status
		if (!paymentStatus.equals("Pending") && !paymentStatus.equals("Completed") && !paymentStatus.equals("Failed")) {
			session.setAttribute("message", "Error: Invalid payment status");
			session.setAttribute("activeSection", "edit-payment");
			return;
		}

		try {
			int id = Integer.parseInt(paymentId);

			PaymentModel payment = new PaymentModel();
			payment.setPaymentId(id);
			payment.setPaymentStatus(paymentStatus);

			boolean success = dashboardService.updatePayment(payment);
			session.setAttribute("message",
					success ? "Payment updated successfully" : "Error: Failed to update payment");
			session.setAttribute("activeSection", "payments");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid payment ID");
			session.setAttribute("activeSection", "edit-payment");
		}
	}

	// Delete Payment
	public static void handleDeletePayment(HttpServletRequest req, HttpSession session) {
		String id = req.getParameter("id");

		try {
			int paymentId = Integer.parseInt(id);
			boolean success = dashboardService.deletePayment(paymentId);
			session.setAttribute("message",
					success ? "Payment deleted successfully" : "Error: Failed to delete payment");
			session.setAttribute("activeSection", "payments");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid payment ID");
			session.setAttribute("activeSection", "payments");
		}
	}

	// Prepare Edit Payment
	public static void prepareEditPayment(HttpServletRequest req) {
		String id = req.getParameter("id");

		try {
			int paymentId = Integer.parseInt(id);
			PaymentModel payment = dashboardService.getPaymentById(paymentId);

			if (payment != null) {
				req.setAttribute("payment", payment);
				req.setAttribute("activeSection", "edit-payment");
			} else {
				req.setAttribute("message", "Error: Payment not found");
				req.setAttribute("activeSection", "payments");
			}
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Error: Invalid payment ID");
			req.setAttribute("activeSection", "payments");
		}
	}

	/**
	 * Handles verifying/unverifying a review.
	 */
	public static void handleVerifyReview(HttpServletRequest req, HttpSession session) {
		String id = req.getParameter("id");
		String isVerified = req.getParameter("isVerified");

		try {
			int reviewId = Integer.parseInt(id);
			boolean verified = "true".equals(isVerified);

			boolean success = dashboardService.verifyReview(reviewId, verified);
			session.setAttribute("message", success ? "Review verification status updated successfully"
					: "Error: Failed to update verification status");
			session.setAttribute("activeSection", "reviews");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid review ID");
			session.setAttribute("activeSection", "reviews");
		}
	}

	/**
	 * Handles deleting a review.
	 */
	public static void handleDeleteReview(HttpServletRequest req, HttpSession session) {
		String id = req.getParameter("id");

		try {
			int reviewId = Integer.parseInt(id);
			boolean success = dashboardService.deleteReview(reviewId);
			session.setAttribute("message", success ? "Review deleted successfully" : "Error: Failed to delete review");
			session.setAttribute("activeSection", "reviews");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid review ID");
			session.setAttribute("activeSection", "reviews");
		}
	}

	public static void handleGenerateReport(HttpServletRequest req, HttpSession session) {
		String userIdStr = req.getParameter("userId");
		String reportType = req.getParameter("reportType");
		String reportScope = req.getParameter("reportScope");
		String startDateStr = req.getParameter("startDate");
		String endDateStr = req.getParameter("endDate");

		System.out.println(
				"handleGenerateReport: userIdStr=[" + userIdStr + "], reportType=[" + reportType + "], reportScope=["
						+ reportScope + "], startDateStr=[" + startDateStr + "], endDateStr=[" + endDateStr + "]");

		if (reportType == null || reportScope == null || startDateStr == null || endDateStr == null) {
			session.setAttribute("message", "Error: All required fields must be filled");
			session.setAttribute("activeSection", "generate-report");
			return;
		}

		try {
			int userId = (userIdStr == null || userIdStr.isEmpty()) ? 0 : Integer.parseInt(userIdStr);
			Date startDate = parseDate(startDateStr);
			Date endDate = parseDate(endDateStr);

			System.out.println("Parsed: startDate=" + startDate + ", endDate=" + endDate);

			if (endDate.before(startDate)) {
				session.setAttribute("message", "Error: End date must be after start date");
				session.setAttribute("activeSection", "generate-report");
				return;
			}

			// Validate userId for User Activity
			if (reportType.equals("User Activity") && userId == 0) {
				session.setAttribute("message", "Error: User Activity requires a specific user");
				session.setAttribute("activeSection", "generate-report");
				System.out.println("Invalid userId for User Activity: userId=0");
				return;
			}

			ReportModel report = dashboardService.generateReport(userId, reportType, reportScope, startDate, endDate);
			if (report != null) {
				session.setAttribute("report", report); // Store in session to persist after redirect
				session.setAttribute("message", "Report generated successfully");
				System.out.println("Report generated: ID=" + report.getReportId());
			} else {
				session.setAttribute("message", "Error: Failed to generate report");
				session.setAttribute("activeSection", "generate-report");
				System.out.println("Report generation failed: returned null");
			}
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Error: Invalid user ID");
			session.setAttribute("activeSection", "generate-report");
			System.out.println("NumberFormatException: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			session.setAttribute("message", "Error: " + e.getMessage());
			session.setAttribute("activeSection", "generate-report");
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static Date parseDate(String dateStr) throws java.text.ParseException {
		String[] patterns = { "yyyy-MM-dd'T'HH:mm", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSS" };
		dateStr = dateStr.trim();
		System.out.println("Parsing date: [" + dateStr + "]");
		for (String pattern : patterns) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				sdf.setLenient(false);
				Date date = sdf.parse(dateStr);
				System.out.println("Parsed with pattern: " + pattern);
				return date;
			} catch (java.text.ParseException e) {
				System.out.println("Failed pattern " + pattern + ": " + e.getMessage());
			}
		}
		throw new java.text.ParseException("Unparseable date: " + dateStr, 0);
	}
}
