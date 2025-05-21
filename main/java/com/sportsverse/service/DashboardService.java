package com.sportsverse.service;

import com.sportsverse.config.DbConfig;
import com.sportsverse.model.AddressModel;
import com.sportsverse.model.BrandModel;
import com.sportsverse.model.CartModel;
import com.sportsverse.model.CategoryModel;
import com.sportsverse.model.OrderModel;
import com.sportsverse.model.OrderProductModel;
import com.sportsverse.model.PaymentMethodModel;
import com.sportsverse.model.PaymentModel;
import com.sportsverse.model.ProductModel;
import com.sportsverse.model.ReportModel;
import com.sportsverse.model.ReportModel.CartItem;
import com.sportsverse.model.ReportModel.InventoryItem;
import com.sportsverse.model.ReportModel.PurchaseItem;
import com.sportsverse.model.ReportModel.SaleItem;
import com.sportsverse.model.ReviewModel;
import com.sportsverse.model.SliderModel;
import com.sportsverse.model.SubcategoryModel;
import com.sportsverse.model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DashboardService handles database operations for fetching dashboard metrics
 * in SportsVerse.
 */
public class DashboardService {

	private Connection dbConn;
	private boolean isConnectionError = false;

	/**
	 * Constructor initializes the database connection.
	 */
	public DashboardService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			isConnectionError = true;
		}
	}

	/**
	 * Fetches the total number of users.
	 * 
	 * @return Integer count of users, or null if an error occurs
	 */
	public Integer getTotalUsers() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT COUNT(*) AS TotalUsers FROM Users";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				return result.getInt("TotalUsers");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches the total number of orders.
	 * 
	 * @return Integer count of orders, or null if an error occurs
	 */
	public Integer getTotalOrders() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT COUNT(*) AS TotalOrders FROM Orders";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				return result.getInt("TotalOrders");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches the number of products with low stock (StockQuantity < 10).
	 * 
	 * @return Integer count of low stock products, or null if an error occurs
	 */
	public Integer getLowStockProducts() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT COUNT(*) AS LowStock FROM Product WHERE StockQuantity < 10";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				return result.getInt("LowStock");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches the total number of brands.
	 * 
	 * @return Integer count of brands, or null if an error occurs
	 */
	public Integer getTotalBrands() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT COUNT(*) AS TotalBrands FROM Brand";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				return result.getInt("TotalBrands");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches the total number of categories.
	 * 
	 * @return Integer count of categories, or null if an error occurs
	 */
	public Integer getTotalCategories() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT COUNT(*) AS TotalCategories FROM Category";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				return result.getInt("TotalCategories");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches the total number of products.
	 * 
	 * @return Integer count of products, or null if an error occurs
	 */
	public Integer getTotalProducts() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT COUNT(*) AS TotalProducts FROM Product";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				return result.getInt("TotalProducts");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches the number of featured products.
	 * 
	 * @return Integer count of featured products, or null if an error occurs
	 */
	public Integer getFeaturedProducts() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT COUNT(*) AS FeaturedProducts FROM Product WHERE IsFeatured = 1";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				return result.getInt("FeaturedProducts");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches the total revenue from completed orders.
	 * 
	 * @return Double sum of TotalAmount from completed orders, or null if an error
	 *         occurs
	 */
	public Double getTotalRevenues() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT SUM(TotalAmount) AS TotalRevenues FROM Orders WHERE OrderPaymentStatus = 'Completed'";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				return result.getDouble("TotalRevenues");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches the 3 most recently joined users.
	 * 
	 * @return List of UserModel objects, or null if an error occurs
	 */
	public List<UserModel> getRecentUsers() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT UserId, FirstName, LastName, Gender, Email, Phone FROM Users WHERE Role = 'customer' ORDER BY UserId DESC LIMIT 3";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			List<UserModel> recentUsers = new ArrayList<>();
			while (result.next()) {
				UserModel user = new UserModel();
				user.setUserId(result.getInt("UserId"));
				user.setFirstName(result.getString("FirstName"));
				user.setLastName(result.getString("LastName"));
				user.setGender(result.getString("Gender"));
				user.setEmail(result.getString("Email"));
				user.setPhone(result.getString("Phone"));
				recentUsers.add(user);
			}
			return recentUsers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches all users from the database.
	 * 
	 * @return List of UserModel objects, or null if an error occurs
	 */
	public List<UserModel> getAllUsers() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT UserId, FirstName, LastName, Gender, Email, Phone, ContactPreference, Role FROM Users";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			List<UserModel> users = new ArrayList<>();
			while (result.next()) {
				UserModel user = new UserModel();
				user.setUserId(result.getInt("UserId"));
				user.setFirstName(result.getString("FirstName"));
				user.setLastName(result.getString("LastName"));
				user.setGender(result.getString("Gender"));
				user.setEmail(result.getString("Email"));
				user.setPhone(result.getString("Phone"));
				user.setContactPreference(result.getString("ContactPreference"));
				user.setRole(result.getString("Role"));
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches all addresses from the database.
	 * 
	 * @return List of AddressModel objects, or null if an error occurs
	 */
	public List<AddressModel> getAllAddresses() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT AddressId, UserId, StreetAddress, City, NepalState, AddressType FROM Address";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			List<AddressModel> addresses = new ArrayList<>();
			while (result.next()) {
				AddressModel address = new AddressModel();
				address.setAddressId(result.getInt("AddressId"));
				address.setUserId(result.getInt("UserId"));
				address.setStreetAddress(result.getString("StreetAddress"));
				address.setCity(result.getString("City"));
				address.setNepalState(result.getString("NepalState"));
				address.setAddressType(result.getString("AddressType"));
				addresses.add(address);
			}
			return addresses;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches all brands from the database.
	 * 
	 * @return List of BrandModel objects, or null if an error occurs
	 */
	public List<BrandModel> getAllBrands() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT BrandId, BrandName FROM Brand";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			List<BrandModel> brands = new ArrayList<>();
			while (result.next()) {
				BrandModel brand = new BrandModel();
				brand.setBrandId(result.getInt("BrandId"));
				brand.setBrandName(result.getString("BrandName"));
				brands.add(brand);
			}
			return brands;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches a brand by its ID.
	 * 
	 * @param brandId The ID of the brand
	 * @return BrandModel object, or null if not found or an error occurs
	 */
	public BrandModel getBrandById(int brandId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT BrandId, BrandName FROM Brand WHERE BrandId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, brandId);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				BrandModel brand = new BrandModel();
				brand.setBrandId(result.getInt("BrandId"));
				brand.setBrandName(result.getString("BrandName"));
				return brand;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Adds a new brand to the database.
	 * 
	 * @param brand The BrandModel object to add
	 * @return true if successful, false otherwise
	 */
	public boolean addBrand(BrandModel brand) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "INSERT INTO Brand (BrandName) VALUES (?)";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, brand.getBrandName());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Updates an existing brand in the database.
	 * 
	 * @param brand The BrandModel object with updated data
	 * @return true if successful, false otherwise
	 */
	public boolean updateBrand(BrandModel brand) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "UPDATE Brand SET BrandName = ? WHERE BrandId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, brand.getBrandName());
			stmt.setInt(2, brand.getBrandId());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Deletes a brand from the database.
	 * 
	 * @param brandId The ID of the brand to delete
	 * @return true if successful, false otherwise
	 */
	public boolean deleteBrand(int brandId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "DELETE FROM Brand WHERE BrandId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, brandId);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Duplication Check
	public boolean isBrandNameExists(String brandName, int excludeBrandId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "SELECT COUNT(*) FROM Brand WHERE LOWER(BrandName) = LOWER(?) AND BrandId != ?";

		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, brandName.trim());
			stmt.setInt(2, excludeBrandId);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Fetches all products from the database.
	 *
	 * @return List of ProductModel objects, or null if an error occurs
	 */
	public List<ProductModel> getAllProducts() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT ProductId, ProductName, SubcategoryId, BrandId, Gender, ProductMaterial, "
				+ "ProductDescription, ProductPrice, ProductSize, StockQuantity, MainImageUrl, "
				+ "ProductRating, IsFeatured FROM Product";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			List<ProductModel> products = new ArrayList<>();
			while (result.next()) {
				ProductModel product = new ProductModel();
				product.setProductId(result.getInt("ProductId"));
				product.setProductName(result.getString("ProductName"));
				product.setSubcategoryId(result.getInt("SubcategoryId"));
				product.setBrandId(result.getInt("BrandId"));
				product.setGender(result.getString("Gender"));
				product.setProductMaterial(result.getString("ProductMaterial"));
				product.setProductDescription(result.getString("ProductDescription"));
				product.setProductPrice(result.getDouble("ProductPrice"));
				product.setProductSize(result.getString("ProductSize"));
				product.setStockQuantity(result.getInt("StockQuantity"));
				product.setMainImageUrl(result.getString("MainImageUrl"));
				product.setProductRating(result.getDouble("ProductRating"));
				product.setFeatured(result.getBoolean("IsFeatured"));
				products.add(product);
			}
			return products;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches products whose name, description, material, brand, sub-category, or
	 * category matches the given search term (case-insensitive).
	 *
	 * @param searchQuery the term to search for
	 * @return list of matching products, or null on error
	 */
	public List<ProductModel> searchProducts(String searchQuery) {

		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		// If the user didn’t type anything, return every product.
		if (searchQuery == null || searchQuery.trim().isEmpty()) {
			return getAllProducts();
		}

		/*
		 * ----------------------------------------------------------- p = Product b =
		 * Brand s = Subcategory c = Category
		 * -----------------------------------------------------------
		 */
		String sql = "SELECT p.ProductId, p.ProductName, p.SubcategoryId, p.BrandId, p.Gender, "
				+ "       p.ProductMaterial, p.ProductDescription, p.ProductPrice, "
				+ "       p.ProductSize, p.StockQuantity, p.MainImageUrl, " + "       p.ProductRating, p.IsFeatured "
				+ "FROM   Product      p " + "JOIN   Brand        b ON p.BrandId       = b.BrandId "
				+ "JOIN   Subcategory  s ON p.SubcategoryId = s.SubcategoryId "
				+ "JOIN   Category     c ON s.CategoryId    = c.CategoryId "
				+ "WHERE  LOWER(p.ProductName)        LIKE ? " + "   OR  LOWER(p.ProductDescription) LIKE ? "
				+ "   OR  LOWER(p.ProductMaterial)    LIKE ? " + "   OR  LOWER(b.BrandName)          LIKE ? "
				+ "   OR  LOWER(s.SubcategoryName)    LIKE ? " + "   OR  LOWER(c.CategoryName)       LIKE ?";

		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {

			String pattern = "%" + searchQuery.trim().toLowerCase() + "%";

			// The same pattern goes into all six placeholders.
			for (int i = 1; i <= 6; i++) {
				stmt.setString(i, pattern);
			}

			ResultSet rs = stmt.executeQuery();
			List<ProductModel> products = new ArrayList<>();

			while (rs.next()) {
				ProductModel p = new ProductModel();
				p.setProductId(rs.getInt("ProductId"));
				p.setProductName(rs.getString("ProductName"));
				p.setSubcategoryId(rs.getInt("SubcategoryId"));
				p.setBrandId(rs.getInt("BrandId"));
				p.setGender(rs.getString("Gender"));
				p.setProductMaterial(rs.getString("ProductMaterial"));
				p.setProductDescription(rs.getString("ProductDescription"));
				p.setProductPrice(rs.getDouble("ProductPrice"));
				p.setProductSize(rs.getString("ProductSize"));
				p.setStockQuantity(rs.getInt("StockQuantity"));
				p.setMainImageUrl(rs.getString("MainImageUrl"));
				p.setProductRating(rs.getDouble("ProductRating"));
				p.setFeatured(rs.getBoolean("IsFeatured"));
				products.add(p);
			}
			return products;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Fetches a product by its ID.
	 *
	 * @param productId The ID of the product
	 * @return ProductModel object, or null if not found or an error occurs
	 */
	public ProductModel getProductById(int productId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT ProductId, ProductName, SubcategoryId, BrandId, Gender, ProductMaterial, "
				+ "ProductDescription, ProductPrice, ProductSize, StockQuantity, MainImageUrl, "
				+ "ProductRating, IsFeatured FROM Product WHERE ProductId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, productId);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				ProductModel product = new ProductModel();
				product.setProductId(result.getInt("ProductId"));
				product.setProductName(result.getString("ProductName"));
				product.setSubcategoryId(result.getInt("SubcategoryId"));
				product.setBrandId(result.getInt("BrandId"));
				product.setGender(result.getString("Gender"));
				product.setProductMaterial(result.getString("ProductMaterial"));
				product.setProductDescription(result.getString("ProductDescription"));
				product.setProductPrice(result.getDouble("ProductPrice"));
				product.setProductSize(result.getString("ProductSize"));
				product.setStockQuantity(result.getInt("StockQuantity"));
				product.setMainImageUrl(result.getString("MainImageUrl"));
				product.setProductRating(result.getDouble("ProductRating"));
				product.setFeatured(result.getBoolean("IsFeatured"));
				return product;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Adds a new product to the database.
	 *
	 * @param product The ProductModel object to add
	 * @return true if successful, false otherwise
	 */
	public boolean addProduct(ProductModel product) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "INSERT INTO Product (ProductName, SubcategoryId, BrandId, Gender, ProductMaterial, "
				+ "ProductDescription, ProductPrice, ProductSize, StockQuantity, MainImageUrl, "
				+ "ProductRating, IsFeatured) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, product.getProductName());
			stmt.setInt(2, product.getSubcategoryId());
			stmt.setInt(3, product.getBrandId());
			stmt.setString(4, product.getGender());
			stmt.setString(5, product.getProductMaterial());
			stmt.setString(6, product.getProductDescription());
			stmt.setDouble(7, product.getProductPrice());
			stmt.setString(8, product.getProductSize());
			stmt.setInt(9, product.getStockQuantity());
			stmt.setString(10, product.getMainImageUrl());
			stmt.setDouble(11, product.getProductRating());
			stmt.setBoolean(12, product.isFeatured());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Updates an existing product in the database.
	 *
	 * @param product The ProductModel object with updated data
	 * @return true if successful, false otherwise
	 */
	public boolean updateProduct(ProductModel product) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "UPDATE Product SET ProductName = ?, SubcategoryId = ?, BrandId = ?, Gender = ?, "
				+ "ProductMaterial = ?, ProductDescription = ?, ProductPrice = ?, ProductSize = ?, "
				+ "StockQuantity = ?, MainImageUrl = ?, ProductRating = ?, IsFeatured = ? WHERE ProductId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, product.getProductName());
			stmt.setInt(2, product.getSubcategoryId());
			stmt.setInt(3, product.getBrandId());
			stmt.setString(4, product.getGender());
			stmt.setString(5, product.getProductMaterial());
			stmt.setString(6, product.getProductDescription());
			stmt.setDouble(7, product.getProductPrice());
			stmt.setString(8, product.getProductSize());
			stmt.setInt(9, product.getStockQuantity());
			stmt.setString(10, product.getMainImageUrl());
			stmt.setDouble(11, product.getProductRating());
			stmt.setBoolean(12, product.isFeatured());
			stmt.setInt(13, product.getProductId());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Deletes a product from the database.
	 *
	 * @param productId The ID of the product to delete
	 * @return true if successful, false otherwise
	 */
	public boolean deleteProduct(int productId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "DELETE FROM Product WHERE ProductId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, productId);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checks if a product name already exists, excluding the specified product ID.
	 *
	 * @param productName      The product name to check
	 * @param excludeProductId The product ID to exclude (use -1 for new products)
	 * @return true if the product name exists, false otherwise
	 */
	public boolean isProductNameExists(String productName, int excludeProductId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "SELECT COUNT(*) FROM Product WHERE LOWER(ProductName) = LOWER(?) AND ProductId != ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, productName.trim());
			stmt.setInt(2, excludeProductId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Fetches a category by its ID.
	 * 
	 * @param categoryId The ID of the category
	 * @return CategoryModel object, or null if not found or an error occurs
	 */
	public CategoryModel getCategoryById(int categoryId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT CategoryId, CategoryName, CategoryImage FROM Category WHERE CategoryId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, categoryId);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				CategoryModel category = new CategoryModel();
				category.setCategoryId(result.getInt("CategoryId"));
				category.setCategoryName(result.getString("CategoryName"));
				category.setCategoryImage(result.getString("CategoryImage"));
				return category;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches all categories from the database.
	 * 
	 * @return List of CategoryModel objects, or null if an error occurs
	 */
	public List<CategoryModel> getAllCategories() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT CategoryId, CategoryName, CategoryImage FROM Category";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			List<CategoryModel> categories = new ArrayList<>();
			while (result.next()) {
				CategoryModel category = new CategoryModel();
				category.setCategoryId(result.getInt("CategoryId"));
				category.setCategoryName(result.getString("CategoryName"));
				category.setCategoryImage(result.getString("CategoryImage"));
				categories.add(category);
			}
			return categories;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Adds a new category to the database.
	 * 
	 * @param category The CategoryModel object to add
	 * @return true if successful, false otherwise
	 */
	public boolean addCategory(CategoryModel category) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "INSERT INTO Category (CategoryName, CategoryImage) VALUES (?, ?)";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, category.getCategoryName());
			stmt.setString(2, category.getCategoryImage());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Updates an existing category in the database.
	 * 
	 * @param category The CategoryModel object with updated data
	 * @return true if successful, false otherwise
	 */
	public boolean updateCategory(CategoryModel category) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "UPDATE Category SET CategoryName = ?, CategoryImage = ? WHERE CategoryId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, category.getCategoryName());
			stmt.setString(2, category.getCategoryImage()); // New line
			stmt.setInt(3, category.getCategoryId());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Deletes a category from the database.
	 * 
	 * @param categoryId The ID of the category to delete
	 * @return true if successful, false otherwise
	 */
	public boolean deleteCategory(int categoryId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "DELETE FROM Category WHERE CategoryId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, categoryId);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checks if a category name already exists, excluding the specified category
	 * ID.
	 * 
	 * @param categoryName      The category name to check
	 * @param excludeCategoryId The category ID to exclude (use -1 for new
	 *                          categories)
	 * @return true if the category name exists, false otherwise
	 */
	public boolean isCategoryNameExists(String categoryName, int excludeCategoryId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "SELECT COUNT(*) FROM Category WHERE LOWER(CategoryName) = LOWER(?) AND CategoryId != ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, categoryName.trim());
			stmt.setInt(2, excludeCategoryId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Fetches all subcategories from the database.
	 *
	 * @return List of SubcategoryModel objects, or null if an error occurs
	 */
	public List<SubcategoryModel> getAllSubcategories() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT SubcategoryId, CategoryId, SubcategoryName FROM Subcategory";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			List<SubcategoryModel> subcategories = new ArrayList<>();
			while (result.next()) {
				SubcategoryModel subcategory = new SubcategoryModel();
				subcategory.setSubcategoryId(result.getInt("SubcategoryId"));
				subcategory.setCategoryId(result.getInt("CategoryId"));
				subcategory.setSubcategoryName(result.getString("SubcategoryName"));
				subcategories.add(subcategory);
			}
			return subcategories;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Fetches a subcategory by its ID.
	 * 
	 * @param subcategoryId The ID of the subcategory
	 * @return SubcategoryModel object, or null if not found or an error occurs
	 */
	public SubcategoryModel getSubcategoryById(int subcategoryId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT SubcategoryId, CategoryId, SubcategoryName FROM Subcategory WHERE SubcategoryId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, subcategoryId);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				SubcategoryModel subcategory = new SubcategoryModel();
				subcategory.setSubcategoryId(result.getInt("SubcategoryId"));
				subcategory.setCategoryId(result.getInt("CategoryId"));
				subcategory.setSubcategoryName(result.getString("SubcategoryName"));
				return subcategory;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Adds a new subcategory to the database.
	 * 
	 * @param subcategory The SubcategoryModel object to add
	 * @return true if successful, false otherwise
	 */
	public boolean addSubcategory(SubcategoryModel subcategory) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "INSERT INTO Subcategory (CategoryId, SubcategoryName) VALUES (?, ?)";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, subcategory.getCategoryId());
			stmt.setString(2, subcategory.getSubcategoryName());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Updates an existing subcategory in the database.
	 * 
	 * @param subcategory The SubcategoryModel object with updated data
	 * @return true if successful, false otherwise
	 */
	public boolean updateSubcategory(SubcategoryModel subcategory) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "UPDATE Subcategory SET CategoryId = ?, SubcategoryName = ? WHERE SubcategoryId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, subcategory.getCategoryId());
			stmt.setString(2, subcategory.getSubcategoryName());
			stmt.setInt(3, subcategory.getSubcategoryId());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Deletes a subcategory from the database.
	 * 
	 * @param subcategoryId The ID of the subcategory to delete
	 * @return true if successful, false otherwise
	 */
	public boolean deleteSubcategory(int subcategoryId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "DELETE FROM Subcategory WHERE SubcategoryId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, subcategoryId);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checks if a subcategory name already exists within the same category,
	 * excluding the specified subcategory ID.
	 * 
	 * @param subcategoryName      The subcategory name to check
	 * @param categoryId           The ID of the category to check within
	 * @param excludeSubcategoryId The subcategory ID to exclude (use -1 for new
	 *                             subcategories)
	 * @return true if the subcategory name exists, false otherwise
	 */
	public boolean isSubcategoryNameExists(String subcategoryName, int categoryId, int excludeSubcategoryId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "SELECT COUNT(*) FROM Subcategory WHERE LOWER(SubcategoryName) = LOWER(?) AND CategoryId = ? AND SubcategoryId != ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, subcategoryName.trim());
			stmt.setInt(2, categoryId);
			stmt.setInt(3, excludeSubcategoryId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Checks if a subcategory has associated products.
	 * 
	 * @param subcategoryId The ID of the subcategory
	 * @return true if products exist, false otherwise
	 */
	public boolean hasProducts(int subcategoryId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "SELECT COUNT(*) FROM Product WHERE SubcategoryId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, subcategoryId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Fetches all sliders from the database.
	 * 
	 * @return List of SliderModel objects, or null if an error occurs
	 */
	public List<SliderModel> getAllSliders() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT SliderId, SliderImageUrl, SliderText, SliderSubText, SliderOrder, IsActive, SliderLink FROM Slider";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			List<SliderModel> sliders = new ArrayList<>();
			while (result.next()) {
				SliderModel slider = new SliderModel();
				slider.setSliderId(result.getInt("SliderId"));
				slider.setSliderImageUrl(result.getString("SliderImageUrl"));
				slider.setSliderText(result.getString("SliderText"));
				slider.setSliderSubText(result.getString("SliderSubText"));
				slider.setSliderOrder(result.getInt("SliderOrder"));
				slider.setActive(result.getBoolean("IsActive"));
				slider.setSliderLink(result.getString("SliderLink"));
				sliders.add(slider);
			}
			return sliders;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Fetches a slider by its ID.
	 * 
	 * @param sliderId The ID of the slider
	 * @return SliderModel object, or null if not found or an error occurs
	 */
	public SliderModel getSliderById(int sliderId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT SliderId, SliderImageUrl, SliderText, SliderSubText, SliderOrder, IsActive, SliderLink FROM Slider WHERE SliderId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, sliderId);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				SliderModel slider = new SliderModel();
				slider.setSliderId(result.getInt("SliderId"));
				slider.setSliderImageUrl(result.getString("SliderImageUrl"));
				slider.setSliderText(result.getString("SliderText"));
				slider.setSliderSubText(result.getString("SliderSubText"));
				slider.setSliderOrder(result.getInt("SliderOrder"));
				slider.setActive(result.getBoolean("IsActive"));
				slider.setSliderLink(result.getString("SliderLink"));
				return slider;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Adds a new slider to the database.
	 * 
	 * @param slider The SliderModel object to add
	 * @return true if successful, false otherwise
	 */
	public boolean addSlider(SliderModel slider) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		// Validate SliderLink
		if (slider.getSliderLink() != null && !slider.getSliderLink().matches("^/[^\\s]*$")) {
			System.out.println("Invalid SliderLink: " + slider.getSliderLink());
			return false;
		}

		String query = "INSERT INTO Slider (SliderImageUrl, SliderText, SliderSubText, SliderOrder, IsActive, SliderLink) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, slider.getSliderImageUrl());
			stmt.setString(2, slider.getSliderText());
			stmt.setString(3, slider.getSliderSubText());
			stmt.setInt(4, slider.getSliderOrder());
			stmt.setBoolean(5, slider.isActive());
			stmt.setString(6, slider.getSliderLink());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Updates an existing slider in the database.
	 * 
	 * @param slider The SliderModel object with updated data
	 * @return true if successful, false otherwise
	 */
	public boolean updateSlider(SliderModel slider) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		// Validate SliderLink
		if (slider.getSliderLink() != null && !slider.getSliderLink().matches("^/[^\\s]*$")) {
			System.out.println("Invalid SliderLink: " + slider.getSliderLink());
			return false;
		}

		String query = "UPDATE Slider SET SliderImageUrl = ?, SliderText = ?, SliderSubText = ?, SliderOrder = ?, IsActive = ?, SliderLink = ? WHERE SliderId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, slider.getSliderImageUrl());
			stmt.setString(2, slider.getSliderText());
			stmt.setString(3, slider.getSliderSubText());
			stmt.setInt(4, slider.getSliderOrder());
			stmt.setBoolean(5, slider.isActive());
			stmt.setString(6, slider.getSliderLink());
			stmt.setInt(7, slider.getSliderId());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Deletes a slider from the database.
	 * 
	 * @param sliderId The ID of the slider to delete
	 * @return true if successful, false otherwise
	 */
	public boolean deleteSlider(int sliderId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "DELETE FROM Slider WHERE SliderId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, sliderId);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checks if a slider order already exists, excluding the specified slider ID.
	 * 
	 * @param sliderOrder     The order to check
	 * @param excludeSliderId The slider ID to exclude (use -1 for new sliders)
	 * @return true if the order exists, false otherwise
	 */
	public boolean isSliderOrderExists(int sliderOrder, int excludeSliderId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "SELECT COUNT(*) FROM Slider WHERE SliderOrder = ? AND SliderId != ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, sliderOrder);
			stmt.setInt(2, excludeSliderId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Fetches all orders with their associated products from the database.
	 * 
	 * @return List of OrderModel objects with populated orderProducts, or null if
	 *         an error occurs
	 */
	public List<OrderModel> getAllOrders() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		// Map to store orders and their products
		Map<Integer, OrderModel> orderMap = new HashMap<>();

		String query = "SELECT o.OrderId, o.UserId, o.OrderDate, o.OrderStatus, o.TotalAmount, o.AddressId, "
				+ "o.PaymentMethodId, o.OrderPaymentStatus, op.ProductId, op.QuantityOrdered, op.UnitPrice, "
				+ "p.ProductName " + "FROM Orders o " + "LEFT JOIN OrderProduct op ON o.OrderId = op.OrderId "
				+ "LEFT JOIN Product p ON op.ProductId = p.ProductId";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				int orderId = result.getInt("OrderId");
				OrderModel order = orderMap.getOrDefault(orderId, new OrderModel());

				// Populate order details if not already set
				if (order.getOrderId() == 0) {
					order.setOrderId(orderId);
					order.setUserId(result.getInt("UserId"));
					order.setOrderDate(result.getTimestamp("OrderDate"));
					order.setOrderStatus(result.getString("OrderStatus"));
					order.setTotalAmount(result.getDouble("TotalAmount"));
					order.setAddressId(result.getInt("AddressId"));
					order.setPaymentMethodId(result.getInt("PaymentMethodId"));
					order.setOrderPaymentStatus(result.getString("OrderPaymentStatus"));
					orderMap.put(orderId, order);
				}

				// Add product details if present
				int productId = result.getInt("ProductId");
				if (!result.wasNull()) {
					OrderProductModel orderProduct = new OrderProductModel();
					orderProduct.setOrderId(orderId);
					orderProduct.setProductId(productId);
					orderProduct.setQuantityOrdered(result.getInt("QuantityOrdered"));
					orderProduct.setUnitPrice(result.getDouble("UnitPrice"));
					order.getOrderProducts().add(orderProduct);
				}
			}
			return new ArrayList<>(orderMap.values());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches an order by its ID with its associated products.
	 * 
	 * @param orderId The ID of the order
	 * @return OrderModel object with populated orderProducts, or null if not found
	 *         or an error occurs
	 */
	public OrderModel getOrderById(int orderId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT o.OrderId, o.UserId, o.OrderDate, o.OrderStatus, o.TotalAmount, o.AddressId, "
				+ "o.PaymentMethodId, o.OrderPaymentStatus, op.ProductId, op.QuantityOrdered, op.UnitPrice, "
				+ "p.ProductName " + "FROM Orders o " + "LEFT JOIN OrderProduct op ON o.OrderId = op.OrderId "
				+ "LEFT JOIN Product p ON op.ProductId = p.ProductId " + "WHERE o.OrderId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, orderId);
			ResultSet result = stmt.executeQuery();
			OrderModel order = null;
			while (result.next()) {
				if (order == null) {
					order = new OrderModel();
					order.setOrderId(result.getInt("OrderId"));
					order.setUserId(result.getInt("UserId"));
					order.setOrderDate(result.getTimestamp("OrderDate"));
					order.setOrderStatus(result.getString("OrderStatus"));
					order.setTotalAmount(result.getDouble("TotalAmount"));
					order.setAddressId(result.getInt("AddressId"));
					order.setPaymentMethodId(result.getInt("PaymentMethodId"));
					order.setOrderPaymentStatus(result.getString("OrderPaymentStatus"));
				}

				// Add product details if present
				int productId = result.getInt("ProductId");
				if (!result.wasNull()) {
					OrderProductModel orderProduct = new OrderProductModel();
					orderProduct.setOrderId(orderId);
					orderProduct.setProductId(productId);
					orderProduct.setQuantityOrdered(result.getInt("QuantityOrdered"));
					orderProduct.setUnitPrice(result.getDouble("UnitPrice"));
					order.getOrderProducts().add(orderProduct);
				}
			}
			return order;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Updates an existing order's status and payment status in the database.
	 * 
	 * @param order The OrderModel object with updated status fields
	 * @return true if successful, false otherwise
	 */
	public boolean updateOrder(OrderModel order) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "UPDATE Orders SET OrderStatus = ?, OrderPaymentStatus = ? WHERE OrderId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, order.getOrderStatus());
			stmt.setString(2, order.getOrderPaymentStatus());
			stmt.setInt(3, order.getOrderId());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Deletes an order from the database.
	 * 
	 * @param orderId The ID of the order to delete
	 * @return true if successful, false otherwise
	 */
	public boolean deleteOrder(int orderId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "DELETE FROM Orders WHERE OrderId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, orderId);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checks if an order has associated payments.
	 * 
	 * @param orderId The ID of the order
	 * @return true if payments exist, false otherwise
	 */
	public boolean hasPayments(int orderId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "SELECT COUNT(*) FROM Payment WHERE OrderId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, orderId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Fetches all cart items from the database.
	 * 
	 * @return List of CartModel objects, or null if an error occurs
	 */
	public List<CartModel> getAllCarts() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT c.CartId, c.UserId, c.ProductId, c.CartQuantity, c.AddedAt " + "FROM Cart c";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			List<CartModel> carts = new ArrayList<>();
			while (result.next()) {
				CartModel cart = new CartModel();
				cart.setCartId(result.getInt("CartId"));
				cart.setUserId(result.getInt("UserId"));
				cart.setProductId(result.getInt("ProductId"));
				cart.setCartQuantity(result.getInt("CartQuantity"));
				cart.setAddedAt(result.getTimestamp("AddedAt"));
				carts.add(cart);
			}
			return carts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches all payment methods from the database.
	 * 
	 * @return List of PaymentMethodModel objects, or null if an error occurs
	 */
	public List<PaymentMethodModel> getAllPaymentMethods() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT PaymentMethodId, MethodName FROM paymentmethod";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			List<PaymentMethodModel> methods = new ArrayList<>();
			while (result.next()) {
				PaymentMethodModel method = new PaymentMethodModel();
				method.setPaymentMethodId(result.getInt("PaymentMethodId"));
				method.setMethodName(result.getString("MethodName"));
				methods.add(method);
			}
			return methods;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches a payment method by its ID.
	 * 
	 * @param paymentMethodId The ID of the payment method
	 * @return PaymentMethodModel object, or null if not found or an error occurs
	 */
	public PaymentMethodModel getPaymentMethodById(int paymentMethodId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT PaymentMethodId, MethodName FROM paymentmethod WHERE PaymentMethodId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, paymentMethodId);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				PaymentMethodModel method = new PaymentMethodModel();
				method.setPaymentMethodId(result.getInt("PaymentMethodId"));
				method.setMethodName(result.getString("MethodName"));
				return method;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Adds a new payment method to the database.
	 * 
	 * @param method The PaymentMethodModel object to add
	 * @return true if successful, false if duplicate or error
	 */
	public boolean addPaymentMethod(PaymentMethodModel method) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		// Check for duplicate method name
		if (isPaymentMethodNameExists(method.getMethodName(), 0)) {
			return false; // Duplicate name
		}

		String query = "INSERT INTO paymentmethod (MethodName) VALUES (?)";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, method.getMethodName());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Updates an existing payment method in the database.
	 * 
	 * @param method The PaymentMethodModel object with updated fields
	 * @return true if successful, false if duplicate or error
	 */
	public boolean updatePaymentMethod(PaymentMethodModel method) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		// Check for duplicate method name (excluding current ID)
		if (isPaymentMethodNameExists(method.getMethodName(), method.getPaymentMethodId())) {
			return false; // Duplicate name
		}

		String query = "UPDATE paymentmethod SET MethodName = ? WHERE PaymentMethodId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, method.getMethodName());
			stmt.setInt(2, method.getPaymentMethodId());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Deletes a payment method from the database.
	 * 
	 * @param paymentMethodId The ID of the payment method to delete
	 * @return true if successful, false otherwise
	 */
	public boolean deletePaymentMethod(int paymentMethodId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "DELETE FROM paymentmethod WHERE PaymentMethodId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, paymentMethodId);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checks if a payment method is used in orders or payments.
	 * 
	 * @param paymentMethodId The ID of the payment method
	 * @return true if used, false otherwise
	 */
	public boolean isPaymentMethodUsed(int paymentMethodId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "SELECT COUNT(*) FROM orders WHERE PaymentMethodId = ? "
				+ "UNION ALL SELECT COUNT(*) FROM payment WHERE PaymentMethodId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, paymentMethodId);
			stmt.setInt(2, paymentMethodId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Checks if a payment method name already exists in the database. For updates,
	 * excludes the current payment method's ID.
	 * 
	 * @param methodName The name to check
	 * @param excludeId  The ID to exclude (use 0 for add operations)
	 * @return true if the name exists, false otherwise
	 */
	public boolean isPaymentMethodNameExists(String methodName, int excludeId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "SELECT COUNT(*) FROM paymentmethod WHERE MethodName = ? AND PaymentMethodId != ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, methodName);
			stmt.setInt(2, excludeId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Fetches all payments from the database.
	 * 
	 * @return List of PaymentModel objects, or null if an error occurs
	 */
	public List<PaymentModel> getAllPayments() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT p.PaymentId, p.OrderId, p.PaymentMethodId, p.PaymentAmount, p.PaymentDate, p.PaymentStatus "
				+ "FROM payment p";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			List<PaymentModel> payments = new ArrayList<>();
			while (result.next()) {
				PaymentModel payment = new PaymentModel();
				payment.setPaymentId(result.getInt("PaymentId"));
				payment.setOrderId(result.getInt("OrderId"));
				payment.setPaymentMethodId(result.getInt("PaymentMethodId"));
				payment.setPaymentAmount(result.getDouble("PaymentAmount"));
				payment.setPaymentDate(result.getTimestamp("PaymentDate"));
				payment.setPaymentStatus(result.getString("PaymentStatus"));
				payments.add(payment);
			}
			return payments;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches a payment by its ID.
	 * 
	 * @param paymentId The ID of the payment
	 * @return PaymentModel object, or null if not found or an error occurs
	 */
	public PaymentModel getPaymentById(int paymentId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT PaymentId, OrderId, PaymentMethodId, PaymentAmount, PaymentDate, PaymentStatus "
				+ "FROM payment WHERE PaymentId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, paymentId);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				PaymentModel payment = new PaymentModel();
				payment.setPaymentId(result.getInt("PaymentId"));
				payment.setOrderId(result.getInt("OrderId"));
				payment.setPaymentMethodId(result.getInt("PaymentMethodId"));
				payment.setPaymentAmount(result.getDouble("PaymentAmount"));
				payment.setPaymentDate(result.getTimestamp("PaymentDate"));
				payment.setPaymentStatus(result.getString("PaymentStatus"));
				return payment;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Updates a payment's status in the database.
	 * 
	 * @param payment The PaymentModel object with updated status
	 * @return true if successful, false otherwise
	 */
	public boolean updatePayment(PaymentModel payment) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "UPDATE payment SET PaymentStatus = ? WHERE PaymentId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, payment.getPaymentStatus());
			stmt.setInt(2, payment.getPaymentId());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Deletes a payment from the database.
	 * 
	 * @param paymentId The ID of the payment to delete
	 * @return true if successful, false otherwise
	 */
	public boolean deletePayment(int paymentId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return false;
		}

		String query = "DELETE FROM payment WHERE PaymentId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, paymentId);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Fetches all reviews from the database.
	 * 
	 * @return List of ReviewModel objects, or null if an error occurs
	 */
	public List<ReviewModel> getAllReviews() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT ReviewId, ProductId, UserId, Rating, ReviewComment, ReviewDate, IsVerified FROM review";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			List<ReviewModel> reviews = new ArrayList<>();
			while (result.next()) {
				ReviewModel review = new ReviewModel();
				review.setReviewId(result.getInt("ReviewId"));
				review.setProductId(result.getInt("ProductId"));
				review.setUserId(result.getInt("UserId"));
				review.setRating(result.getFloat("Rating"));
				review.setReviewComment(result.getString("ReviewComment"));
				review.setReviewDate(result.getTimestamp("ReviewDate"));
				review.setVerified(result.getBoolean("IsVerified"));
				reviews.add(review);
			}
			return reviews;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Toggles the verification status of a review by ID.
	 * 
	 * @param reviewId   The ID of the review to verify or unverify.
	 * @param isVerified The new verification status (true for verified, false for
	 *                   unverified).
	 * @return true if the update is successful, false otherwise.
	 */
	public boolean verifyReview(int reviewId, boolean isVerified) {
		if (isConnectionError)
			return false;
		String query = "UPDATE review SET IsVerified = ? WHERE ReviewId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setBoolean(1, isVerified);
			stmt.setInt(2, reviewId);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Deletes a review from the database.
	 * 
	 * @param reviewId The ID of the review to delete.
	 * @return true if the deletion is successful, false otherwise.
	 */
	public boolean deleteReview(int reviewId) {
		if (isConnectionError)
			return false;
		String query = "DELETE FROM review WHERE ReviewId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, reviewId);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Generates a report based on user ID, report type, scope, and date range.
	 * 
	 * @param userId      The user ID (0 for all users in Sales, non-zero for User
	 *                    Activity)
	 * @param reportType  The type of report (Sales, User Activity, Inventory)
	 * @param reportScope The scope (Daily, Weekly, Monthly)
	 * @param startDate   The start date
	 * @param endDate     The end date
	 * @return ReportModel object, or null if an error occurs
	 */
	public ReportModel generateReport(int userId, String reportType, String reportScope, Date startDate, Date endDate) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		System.out.println("Generating report: userId=" + userId + ", type=" + reportType + ", scope=" + reportScope
				+ ", startDate=" + startDate + ", endDate=" + endDate);

		ReportModel report = new ReportModel();
		report.setUserId(userId);
		report.setReportType(reportType);
		report.setReportScope(reportScope);
		report.setStartDate(startDate);
		report.setEndDate(endDate);

		try {
			// Set user name for single-user reports (except Inventory and Sales with
			// userId=0)
			if (userId != 0 && !reportType.equals("Inventory")) {
				String userQuery = "SELECT FirstName, LastName FROM Users WHERE UserId = ?";
				try (PreparedStatement stmt = dbConn.prepareStatement(userQuery)) {
					stmt.setInt(1, userId);
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						report.setUserName(rs.getString("FirstName") + " " + rs.getString("LastName"));
					} else {
						System.out.println("User not found for ID: " + userId);
						return null;
					}
				}
			}

			if (reportType.equals("Sales")) {
				String query = userId == 0
						? "SELECT p.ProductId, p.ProductName, u.FirstName, u.LastName, "
								+ "SUM(op.QuantityOrdered) AS QuantitySold, "
								+ "SUM(op.QuantityOrdered * op.UnitPrice) AS TotalAmount "
								+ "FROM OrderProduct op JOIN Orders o ON op.OrderId = o.OrderId "
								+ "JOIN Product p ON op.ProductId = p.ProductId "
								+ "JOIN Users u ON o.UserId = u.UserId " + "WHERE o.OrderDate BETWEEN ? AND ? "
								+ "GROUP BY p.ProductId, p.ProductName, u.UserId, u.FirstName, u.LastName"
						: "SELECT p.ProductId, p.ProductName, " + "SUM(op.QuantityOrdered) AS QuantitySold, "
								+ "SUM(op.QuantityOrdered * op.UnitPrice) AS TotalAmount "
								+ "FROM OrderProduct op JOIN Orders o ON op.OrderId = o.OrderId "
								+ "JOIN Product p ON op.ProductId = p.ProductId "
								+ "WHERE o.UserId = ? AND o.OrderDate BETWEEN ? AND ? "
								+ "GROUP BY p.ProductId, p.ProductName";
				System.out.println("Executing Sales query: " + query);
				try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
					if (userId == 0) {
						stmt.setTimestamp(1, new Timestamp(startDate.getTime()));
						stmt.setTimestamp(2, new Timestamp(endDate.getTime()));
					} else {
						stmt.setInt(1, userId);
						stmt.setTimestamp(2, new Timestamp(startDate.getTime()));
						stmt.setTimestamp(3, new Timestamp(endDate.getTime()));
					}
					ResultSet rs = stmt.executeQuery();
					List<SaleItem> salesItems = new ArrayList<>();
					int totalOrders = 0;
					double totalSpent = 0.0;
					while (rs.next()) {
						int quantity = rs.getInt("QuantitySold");
						double amount = rs.getDouble("TotalAmount");
						String userName = userId == 0 ? rs.getString("FirstName") + " " + rs.getString("LastName")
								: report.getUserName();
						salesItems.add(new SaleItem(rs.getInt("ProductId"), rs.getString("ProductName"), userName,
								quantity, amount));
						totalOrders += quantity;
						totalSpent += amount;
					}
					report.setSalesItems(salesItems);
					report.setTotalOrders(totalOrders);
					report.setTotalSpent(totalSpent);
					System.out.println("Sales report: items=" + salesItems.size() + ", orders=" + totalOrders
							+ ", spent=" + totalSpent);
				}
			} else if (reportType.equals("User Activity")) {
				if (userId == 0) {
					System.out.println("User Activity report requires a specific user");
					return null;
				}
				// Purchases
				String purchaseQuery = "SELECT o.OrderId, p.ProductId, p.ProductName, op.QuantityOrdered AS Quantity, "
						+ "(op.QuantityOrdered * op.UnitPrice) AS Amount "
						+ "FROM OrderProduct op JOIN Orders o ON op.OrderId = o.OrderId "
						+ "JOIN Product p ON op.ProductId = p.ProductId "
						+ "WHERE o.UserId = ? AND o.OrderDate BETWEEN ? AND ?";
				System.out.println("Executing Purchase query: " + purchaseQuery);
				try (PreparedStatement stmt = dbConn.prepareStatement(purchaseQuery)) {
					stmt.setInt(1, userId);
					stmt.setTimestamp(2, new Timestamp(startDate.getTime()));
					stmt.setTimestamp(3, new Timestamp(endDate.getTime()));
					ResultSet rs = stmt.executeQuery();
					List<PurchaseItem> purchaseItems = new ArrayList<>();
					int totalOrders = 0;
					double totalSpent = 0.0;
					while (rs.next()) {
						int quantity = rs.getInt("Quantity");
						double amount = rs.getDouble("Amount");
						purchaseItems.add(new PurchaseItem(rs.getInt("OrderId"), rs.getInt("ProductId"),
								rs.getString("ProductName"), quantity, amount));
						totalOrders += quantity;
						totalSpent += amount;
					}
					report.setPurchaseItems(purchaseItems);
					report.setTotalOrders(totalOrders);
					report.setTotalSpent(totalSpent);
					System.out.println("Purchase report: items=" + purchaseItems.size() + ", orders=" + totalOrders
							+ ", spent=" + totalSpent);
				}
				// Cart
				String cartQuery = "SELECT p.ProductId, p.ProductName, c.CartQuantity AS Quantity "
						+ "FROM Cart c JOIN Product p ON c.ProductId = p.ProductId WHERE c.UserId = ?";
				System.out.println("Executing Cart query: " + cartQuery);
				try (PreparedStatement stmt = dbConn.prepareStatement(cartQuery)) {
					stmt.setInt(1, userId);
					ResultSet rs = stmt.executeQuery();
					List<CartItem> cartItems = new ArrayList<>();
					while (rs.next()) {
						cartItems.add(new CartItem(rs.getInt("ProductId"), rs.getString("ProductName"),
								rs.getInt("Quantity")));
					}
					report.setCartItems(cartItems);
					System.out.println("Cart report: items=" + cartItems.size());
				}
			} else if (reportType.equals("Inventory")) {
				String query = "SELECT ProductId, ProductName, StockQuantity, ProductPrice AS Price FROM Product";
				System.out.println("Executing Inventory query: " + query);
				try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
					ResultSet rs = stmt.executeQuery();
					List<InventoryItem> inventoryItems = new ArrayList<>();
					int totalProducts = 0;
					int lowStockCount = 0;
					double totalValue = 0.0;
					while (rs.next()) {
						int stock = rs.getInt("StockQuantity");
						double price = rs.getDouble("Price");
						boolean lowStock = stock < 10;
						inventoryItems.add(new InventoryItem(rs.getInt("ProductId"), rs.getString("ProductName"), stock,
								price, lowStock));
						totalProducts++;
						if (lowStock) {
							lowStockCount++;
						}
						totalValue += stock * price;
					}
					report.setInventoryItems(inventoryItems);
					report.setTotalProducts(totalProducts);
					report.setLowStockCount(lowStockCount);
					report.setTotalOrders(totalProducts); // Using totalOrders for consistency
					report.setTotalSpent(totalValue);
					System.out.println("Inventory report: items=" + inventoryItems.size() + ", products="
							+ totalProducts + ", lowStock=" + lowStockCount + ", value=" + totalValue);
				}
			} else {
				System.out.println("Invalid report type: " + reportType);
				return null;
			}

			// Save to report table
			String insertQuery = "INSERT INTO report (ReportId, UserId, ReportType, ReportScope, StartDate, EndDate, TotalOrders, TotalSpent) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement stmt = dbConn.prepareStatement(insertQuery)) {
				int reportId = generateReportId();
				report.setReportId(reportId);
				stmt.setInt(1, reportId);
				if ((reportType.equals("Inventory") || reportType.equals("Sales")) && userId == 0) {
					stmt.setNull(2, java.sql.Types.INTEGER); // Set UserId to NULL for Inventory and Sales with All
																// Users
				} else {
					stmt.setInt(2, userId);
				}
				stmt.setString(3, reportType);
				stmt.setString(4, reportScope);
				stmt.setTimestamp(5, new Timestamp(startDate.getTime()));
				stmt.setTimestamp(6, new Timestamp(endDate.getTime()));
				stmt.setInt(7, report.getTotalOrders());
				stmt.setDouble(8, report.getTotalSpent());
				stmt.executeUpdate();
				System.out.println("Saved report: ID=" + reportId);
			} catch (SQLException e) {
				System.out.println("Error saving report to database: " + e.getMessage());
				e.printStackTrace();
				return null;
			}

			return report;
		} catch (SQLException e) {
			System.out.println("SQL Error in report generation: " + e.getMessage());
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println("Unexpected error in report generation: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	private int generateReportId() {
		String query = "SELECT MAX(ReportId) AS MaxId FROM report";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("MaxId") + 1;
			}
		} catch (SQLException e) {
			System.out.println("Error generating report ID: " + e.getMessage());
			e.printStackTrace();
		}
		return (int) (Math.random() * 1000000);
	}

}