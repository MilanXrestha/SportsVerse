package com.sportsverse.service;

import com.sportsverse.config.DbConfig;
import com.sportsverse.model.ProductModel;
import com.sportsverse.model.ReviewModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductService {

	public ProductModel getProductById(int productId) throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM product WHERE ProductId = ?";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, productId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					ProductModel product = new ProductModel();
					product.setProductId(rs.getInt("ProductId"));
					product.setProductName(rs.getString("ProductName"));
					product.setSubcategoryId(rs.getInt("SubcategoryId"));
					product.setBrandId(rs.getInt("BrandId"));
					product.setGender(rs.getString("Gender"));
					product.setProductMaterial(rs.getString("ProductMaterial"));
					product.setProductDescription(rs.getString("ProductDescription"));
					product.setProductPrice(rs.getDouble("ProductPrice"));
					product.setProductSize(rs.getString("ProductSize"));
					product.setStockQuantity(rs.getInt("StockQuantity"));
					product.setMainImageUrl(rs.getString("MainImageUrl"));
					product.setProductRating(rs.getDouble("ProductRating"));
					product.setFeatured(rs.getBoolean("IsFeatured"));
					return product;
				}
			}
		}
		return null;
	}

	public String getBrandName(int brandId) throws SQLException, ClassNotFoundException {
		String sql = "SELECT BrandName FROM brand WHERE BrandId = ?";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, brandId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("BrandName");
				}
			}
		}
		return "Unknown Brand";
	}

	public List<ReviewModel> getReviewsByProductId(int productId) throws SQLException, ClassNotFoundException {
		List<ReviewModel> reviews = new ArrayList<>();
		String sql = "SELECT * FROM review WHERE ProductId = ? ORDER BY ReviewDate DESC";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, productId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					ReviewModel review = new ReviewModel();
					review.setReviewId(rs.getInt("ReviewId"));
					review.setProductId(rs.getInt("ProductId"));
					review.setUserId(rs.getInt("UserId"));
					review.setRating(rs.getFloat("Rating"));
					review.setReviewComment(rs.getString("ReviewComment"));
					review.setReviewDate(rs.getTimestamp("ReviewDate"));
					review.setVerified(rs.getBoolean("IsVerified"));
					reviews.add(review);
				}
			}
		}
		return reviews;
	}

	public void addReview(int userId, int productId, float rating, String reviewComment)
			throws SQLException, ClassNotFoundException {
		// Check for duplicate review
		String checkSql = "SELECT ReviewId FROM review WHERE UserId = ? AND ProductId = ?";
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
			checkStmt.setInt(1, userId);
			checkStmt.setInt(2, productId);
			try (ResultSet rs = checkStmt.executeQuery()) {
				if (rs.next()) {
					return; // User already reviewed this product
				}
			}
		}

		// Insert review
		String insertSql = "INSERT INTO review (ProductId, UserId, Rating, ReviewComment, ReviewDate, IsVerified) VALUES (?, ?, ?, ?, NOW(), ?)";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(insertSql)) {
			stmt.setInt(1, productId);
			stmt.setInt(2, userId);
			stmt.setFloat(3, rating);
			stmt.setString(4, reviewComment);
			stmt.setBoolean(5, false); // Reviews start unverified
			stmt.executeUpdate();
		}

		// Update product rating
		updateProductRating(productId);
	}

	private void updateProductRating(int productId) throws SQLException, ClassNotFoundException {
		String sql = "SELECT AVG(Rating) as AvgRating FROM review WHERE ProductId = ?";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, productId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					double avgRating = rs.getDouble("AvgRating");
					String updateSql = "UPDATE product SET ProductRating = ? WHERE ProductId = ?";
					try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
						updateStmt.setDouble(1, avgRating);
						updateStmt.setInt(2, productId);
						updateStmt.executeUpdate();
					}
				}
			}
		}
	}
}