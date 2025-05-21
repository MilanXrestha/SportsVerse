package com.sportsverse.service;

import com.sportsverse.config.DbConfig;
import com.sportsverse.model.CategoryModel;
import com.sportsverse.model.ProductModel;
import com.sportsverse.model.SubcategoryModel;
import com.sportsverse.model.SliderModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeService {

	// Fetches all categories with their subcategories
	public Map<CategoryModel, List<SubcategoryModel>> getAllCategoriesWithSubcategories()
			throws SQLException, ClassNotFoundException {
		Map<CategoryModel, List<SubcategoryModel>> categoryMap = new HashMap<>();
		List<CategoryModel> categories = new ArrayList<>();

		String categorySql = "SELECT CategoryId, CategoryName, CategoryImage FROM category";
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(categorySql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("CategoryId");
				String name = rs.getString("CategoryName");
				String image = rs.getString("CategoryImage");
				categories.add(new CategoryModel(id, name, image));
			}
		}

		String subcategorySql = "SELECT SubcategoryId, CategoryId, SubcategoryName FROM subcategory WHERE CategoryId = ?";
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(subcategorySql)) {
			for (CategoryModel category : categories) {
				List<SubcategoryModel> subcategories = new ArrayList<>();
				stmt.setInt(1, category.getCategoryId());
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						int subcategoryId = rs.getInt("SubcategoryId");
						int categoryId = rs.getInt("CategoryId");
						String subcategoryName = rs.getString("SubcategoryName");
						subcategories.add(new SubcategoryModel(subcategoryId, categoryId, subcategoryName));
					}
				}
				categoryMap.put(category, subcategories);
			}
		}

		return categoryMap;
	}

	// Fetches active sliders for the homepage
	public List<SliderModel> getActiveSliders() throws SQLException, ClassNotFoundException {
		List<SliderModel> sliders = new ArrayList<>();
		String sql = "SELECT SliderId, SliderImageUrl, SliderText, SliderSubText, SliderOrder, IsActive, SliderLink "
				+ "FROM slider WHERE IsActive = 1 ORDER BY SliderOrder";
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				SliderModel slider = new SliderModel();
				slider.setSliderId(rs.getInt("SliderId"));
				slider.setSliderImageUrl(rs.getString("SliderImageUrl"));
				slider.setSliderText(rs.getString("SliderText"));
				slider.setSliderSubText(rs.getString("SliderSubText"));
				slider.setSliderOrder(rs.getInt("SliderOrder"));
				slider.setActive(rs.getBoolean("IsActive"));
				slider.setSliderLink(rs.getString("SliderLink"));
				sliders.add(slider);
			}
		}
		return sliders;
	}

	// Fetches featured products
	public List<ProductModel> getFeaturedProducts() throws SQLException, ClassNotFoundException {
		List<ProductModel> featuredProducts = new ArrayList<>();
		String sql = "SELECT ProductId, ProductName, SubcategoryId, BrandId, Gender, ProductMaterial, ProductDescription, "
				+ "ProductPrice, ProductSize, StockQuantity, MainImageUrl, ProductRating, IsFeatured "
				+ "FROM product WHERE IsFeatured = 1";
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				ProductModel product = new ProductModel(rs.getInt("ProductId"), rs.getString("ProductName"),
						rs.getInt("SubcategoryId"), rs.getInt("BrandId"), rs.getString("Gender"),
						rs.getString("ProductMaterial"), rs.getString("ProductDescription"),
						rs.getDouble("ProductPrice"), rs.getString("ProductSize"), rs.getInt("StockQuantity"),
						rs.getString("MainImageUrl"), rs.getDouble("ProductRating"), rs.getBoolean("IsFeatured"));
				featuredProducts.add(product);
			}
		}
		return featuredProducts;
	}

	// Fetches products by Adidas brand
	public List<ProductModel> getAdidasProducts() throws SQLException, ClassNotFoundException {
		List<ProductModel> adidasProducts = new ArrayList<>();
		String sql = "SELECT p.ProductId, p.ProductName, p.SubcategoryId, p.BrandId, p.Gender, p.ProductMaterial, "
				+ "p.ProductDescription, p.ProductPrice, p.ProductSize, p.StockQuantity, p.MainImageUrl, p.ProductRating, p.IsFeatured "
				+ "FROM product p JOIN brand b ON p.BrandId = b.BrandId WHERE b.BrandName = ?";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, "Adidas");
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					ProductModel product = new ProductModel(rs.getInt("ProductId"), rs.getString("ProductName"),
							rs.getInt("SubcategoryId"), rs.getInt("BrandId"), rs.getString("Gender"),
							rs.getString("ProductMaterial"), rs.getString("ProductDescription"),
							rs.getDouble("ProductPrice"), rs.getString("ProductSize"), rs.getInt("StockQuantity"),
							rs.getString("MainImageUrl"), rs.getDouble("ProductRating"), rs.getBoolean("IsFeatured"));
					adidasProducts.add(product);
				}
			}
		}
		return adidasProducts;
	}

	// Fetches the top 5 most wishlisted products
	public List<ProductModel> getMostWishlistedProducts() throws SQLException, ClassNotFoundException {
		List<ProductModel> wishlistedProducts = new ArrayList<>();
		String sql = "SELECT p.ProductId, p.ProductName, p.SubcategoryId, p.BrandId, p.Gender, p.ProductMaterial, "
				+ "p.ProductDescription, p.ProductPrice, p.ProductSize, p.StockQuantity, p.MainImageUrl, p.ProductRating, p.IsFeatured "
				+ "FROM product p " + "JOIN wishlist w ON p.ProductId = w.ProductId " + "GROUP BY p.ProductId "
				+ "ORDER BY COUNT(w.WishlistId) DESC " + "LIMIT 5";
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				ProductModel product = new ProductModel(rs.getInt("ProductId"), rs.getString("ProductName"),
						rs.getInt("SubcategoryId"), rs.getInt("BrandId"), rs.getString("Gender"),
						rs.getString("ProductMaterial"), rs.getString("ProductDescription"),
						rs.getDouble("ProductPrice"), rs.getString("ProductSize"), rs.getInt("StockQuantity"),
						rs.getString("MainImageUrl"), rs.getDouble("ProductRating"), rs.getBoolean("IsFeatured"));
				wishlistedProducts.add(product);
			}
		}
		return wishlistedProducts;
	}

	// Fetches all products
	public List<ProductModel> getAllProducts() throws SQLException, ClassNotFoundException {
		List<ProductModel> allProducts = new ArrayList<>();
		String sql = "SELECT ProductId, ProductName, SubcategoryId, BrandId, Gender, ProductMaterial, "
				+ "ProductDescription, ProductPrice, ProductSize, StockQuantity, MainImageUrl, ProductRating, IsFeatured "
				+ "FROM product";
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				ProductModel product = new ProductModel(rs.getInt("ProductId"), rs.getString("ProductName"),
						rs.getInt("SubcategoryId"), rs.getInt("BrandId"), rs.getString("Gender"),
						rs.getString("ProductMaterial"), rs.getString("ProductDescription"),
						rs.getDouble("ProductPrice"), rs.getString("ProductSize"), rs.getInt("StockQuantity"),
						rs.getString("MainImageUrl"), rs.getDouble("ProductRating"), rs.getBoolean("IsFeatured"));
				allProducts.add(product);
			}
		}
		return allProducts;
	}

	// Fetches all categories with their associated products
	public Map<CategoryModel, List<ProductModel>> getCategoriesWithProducts()
			throws SQLException, ClassNotFoundException {
		Map<CategoryModel, List<ProductModel>> categoryProductMap = new HashMap<>();
		List<CategoryModel> categories = new ArrayList<>();

		String categorySql = "SELECT CategoryId, CategoryName, CategoryImage FROM category";
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(categorySql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("CategoryId");
				String name = rs.getString("CategoryName");
				String image = rs.getString("CategoryImage");
				categories.add(new CategoryModel(id, name, image));
			}
		}

		String productSql = "SELECT p.ProductId, p.ProductName, p.SubcategoryId, p.BrandId, p.Gender, p.ProductMaterial, "
				+ "p.ProductDescription, p.ProductPrice, p.ProductSize, p.StockQuantity, p.MainImageUrl, "
				+ "p.ProductRating, p.IsFeatured "
				+ "FROM product p JOIN subcategory s ON p.SubcategoryId = s.SubcategoryId WHERE s.CategoryId = ?";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(productSql)) {
			for (CategoryModel category : categories) {
				List<ProductModel> products = new ArrayList<>();
				stmt.setInt(1, category.getCategoryId());
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						ProductModel product = new ProductModel(rs.getInt("ProductId"), rs.getString("ProductName"),
								rs.getInt("SubcategoryId"), rs.getInt("BrandId"), rs.getString("Gender"),
								rs.getString("ProductMaterial"), rs.getString("ProductDescription"),
								rs.getDouble("ProductPrice"), rs.getString("ProductSize"), rs.getInt("StockQuantity"),
								rs.getString("MainImageUrl"), rs.getDouble("ProductRating"),
								rs.getBoolean("IsFeatured"));
						products.add(product);
					}
				}
				categoryProductMap.put(category, products);
			}
		}

		return categoryProductMap;
	}

	// Fetches products matching a search query, organized by category
	public Map<CategoryModel, List<ProductModel>> searchProducts(String query)
			throws SQLException, ClassNotFoundException {
		Map<CategoryModel, List<ProductModel>> categoryProductMap = new HashMap<>();
		List<CategoryModel> categories = new ArrayList<>();

		String categorySql = "SELECT CategoryId, CategoryName, CategoryImage FROM category";
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(categorySql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("CategoryId");
				String name = rs.getString("CategoryName");
				String image = rs.getString("CategoryImage");
				categories.add(new CategoryModel(id, name, image));
			}
		}

		String productSql = "SELECT p.ProductId, p.ProductName, p.SubcategoryId, p.BrandId, p.Gender, p.ProductMaterial, "
				+ "p.ProductDescription, p.ProductPrice, p.ProductSize, p.StockQuantity, p.MainImageUrl, "
				+ "p.ProductRating, p.IsFeatured FROM product p "
				+ "JOIN subcategory s ON p.SubcategoryId = s.SubcategoryId "
				+ "JOIN category c ON s.CategoryId = c.CategoryId JOIN brand b ON p.BrandId = b.BrandId "
				+ "WHERE s.CategoryId = ? AND (LOWER(p.ProductName) LIKE ? OR LOWER(b.BrandName) LIKE ? OR "
				+ "LOWER(c.CategoryName) LIKE ? OR LOWER(s.SubcategoryName) LIKE ?)";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(productSql)) {
			for (CategoryModel category : categories) {
				List<ProductModel> products = new ArrayList<>();
				String searchPattern = "%" + query.toLowerCase() + "%";
				stmt.setInt(1, category.getCategoryId());
				stmt.setString(2, searchPattern);
				stmt.setString(3, searchPattern);
				stmt.setString(4, searchPattern);
				stmt.setString(5, searchPattern);
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						ProductModel product = new ProductModel(rs.getInt("ProductId"), rs.getString("ProductName"),
								rs.getInt("SubcategoryId"), rs.getInt("BrandId"), rs.getString("Gender"),
								rs.getString("ProductMaterial"), rs.getString("ProductDescription"),
								rs.getDouble("ProductPrice"), rs.getString("ProductSize"), rs.getInt("StockQuantity"),
								rs.getString("MainImageUrl"), rs.getDouble("ProductRating"),
								rs.getBoolean("IsFeatured"));
						products.add(product);
					}
				}
				if (!products.isEmpty()) {
					categoryProductMap.put(category, products);
				}
			}
		}

		return categoryProductMap;
	}

	// Fetches new arrival products, limited to 10 per category
	public Map<CategoryModel, List<ProductModel>> getNewArrivalProducts() throws SQLException, ClassNotFoundException {
		Map<CategoryModel, List<ProductModel>> categoryProductMap = new HashMap<>();
		List<CategoryModel> categories = new ArrayList<>();

		String categorySql = "SELECT CategoryId, CategoryName, CategoryImage FROM category";
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(categorySql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("CategoryId");
				String name = rs.getString("CategoryName");
				String image = rs.getString("CategoryImage");
				categories.add(new CategoryModel(id, name, image));
			}
		}

		String productSql = "SELECT p.ProductId, p.ProductName, p.SubcategoryId, p.BrandId, p.Gender, p.ProductMaterial, "
				+ "p.ProductDescription, p.ProductPrice, p.ProductSize, p.StockQuantity, p.MainImageUrl, "
				+ "p.ProductRating, p.IsFeatured "
				+ "FROM product p JOIN subcategory s ON p.SubcategoryId = s.SubcategoryId "
				+ "WHERE s.CategoryId = ? ORDER BY p.ProductId DESC LIMIT 10";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(productSql)) {
			for (CategoryModel category : categories) {
				List<ProductModel> products = new ArrayList<>();
				stmt.setInt(1, category.getCategoryId());
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						ProductModel product = new ProductModel(rs.getInt("ProductId"), rs.getString("ProductName"),
								rs.getInt("SubcategoryId"), rs.getInt("BrandId"), rs.getString("Gender"),
								rs.getString("ProductMaterial"), rs.getString("ProductDescription"),
								rs.getDouble("ProductPrice"), rs.getString("ProductSize"), rs.getInt("StockQuantity"),
								rs.getString("MainImageUrl"), rs.getDouble("ProductRating"),
								rs.getBoolean("IsFeatured"));
						products.add(product);
					}
				}
				if (!products.isEmpty()) {
					categoryProductMap.put(category, products);
				}
			}
		}

		return categoryProductMap;
	}

	// Fetches products for a specific subcategory
	public Map<CategoryModel, List<ProductModel>> getProductsBySubcategory(int categoryId, int subcategoryId)
			throws SQLException, ClassNotFoundException {
		Map<CategoryModel, List<ProductModel>> categoryProductMap = new HashMap<>();
		CategoryModel category = null;

		// Fetch the specific category
		String categorySql = "SELECT CategoryId, CategoryName, CategoryImage FROM category WHERE CategoryId = ?";
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(categorySql)) {
			stmt.setInt(1, categoryId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int id = rs.getInt("CategoryId");
					String name = rs.getString("CategoryName");
					String image = rs.getString("CategoryImage");
					category = new CategoryModel(id, name, image);
				}
			}
		}

		if (category != null) {
			// Fetch products for the specific subcategory
			List<ProductModel> products = new ArrayList<>();
			String productSql = "SELECT p.ProductId, p.ProductName, p.SubcategoryId, p.BrandId, p.Gender, p.ProductMaterial, "
					+ "p.ProductDescription, p.ProductPrice, p.ProductSize, p.StockQuantity, p.MainImageUrl, "
					+ "p.ProductRating, p.IsFeatured " + "FROM product p WHERE p.SubcategoryId = ?";
			try (Connection conn = DbConfig.getDbConnection();
					PreparedStatement stmt = conn.prepareStatement(productSql)) {
				stmt.setInt(1, subcategoryId);
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						ProductModel product = new ProductModel(rs.getInt("ProductId"), rs.getString("ProductName"),
								rs.getInt("SubcategoryId"), rs.getInt("BrandId"), rs.getString("Gender"),
								rs.getString("ProductMaterial"), rs.getString("ProductDescription"),
								rs.getDouble("ProductPrice"), rs.getString("ProductSize"), rs.getInt("StockQuantity"),
								rs.getString("MainImageUrl"), rs.getDouble("ProductRating"),
								rs.getBoolean("IsFeatured"));
						products.add(product);
					}
				}
			}
			categoryProductMap.put(category, products);
		}

		return categoryProductMap;
	}

	// Fetches products for a specific category
	public Map<CategoryModel, List<ProductModel>> getProductsByCategory(int categoryId)
			throws SQLException, ClassNotFoundException {
		Map<CategoryModel, List<ProductModel>> categoryProductMap = new HashMap<>();
		CategoryModel category = null;

		// Fetch the specific category details
		String categorySql = "SELECT CategoryId, CategoryName, CategoryImage FROM category WHERE CategoryId = ?";
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(categorySql)) {
			stmt.setInt(1, categoryId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int id = rs.getInt("CategoryId");
					String name = rs.getString("CategoryName");
					String image = rs.getString("CategoryImage");
					category = new CategoryModel(id, name, image);
				}
			}
		}

		if (category != null) {
			// Fetch products associated with the category through subcategories
			List<ProductModel> products = new ArrayList<>();
			String productSql = "SELECT p.ProductId, p.ProductName, p.SubcategoryId, p.BrandId, p.Gender, p.ProductMaterial, "
					+ "p.ProductDescription, p.ProductPrice, p.ProductSize, p.StockQuantity, p.MainImageUrl, "
					+ "p.ProductRating, p.IsFeatured "
					+ "FROM product p JOIN subcategory s ON p.SubcategoryId = s.SubcategoryId WHERE s.CategoryId = ?";
			try (Connection conn = DbConfig.getDbConnection();
					PreparedStatement stmt = conn.prepareStatement(productSql)) {
				stmt.setInt(1, categoryId);
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						ProductModel product = new ProductModel(rs.getInt("ProductId"), rs.getString("ProductName"),
								rs.getInt("SubcategoryId"), rs.getInt("BrandId"), rs.getString("Gender"),
								rs.getString("ProductMaterial"), rs.getString("ProductDescription"),
								rs.getDouble("ProductPrice"), rs.getString("ProductSize"), rs.getInt("StockQuantity"),
								rs.getString("MainImageUrl"), rs.getDouble("ProductRating"),
								rs.getBoolean("IsFeatured"));
						products.add(product);
					}
				}
			}
			categoryProductMap.put(category, products);
		}

		return categoryProductMap;
	}
}