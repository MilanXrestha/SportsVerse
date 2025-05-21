package com.sportsverse.service;

import com.sportsverse.config.DbConfig;
import com.sportsverse.model.CartModel;
import com.sportsverse.model.ProductModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartService {

	// Add a product to the cart
	public boolean addToCart(int userId, int productId, int quantity) throws SQLException, ClassNotFoundException {
		String stockSql = "SELECT StockQuantity FROM product WHERE ProductId = ?";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(stockSql)) {
			stmt.setInt(1, productId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next() && rs.getInt("StockQuantity") >= quantity) {
					String checkSql = "SELECT CartId, CartQuantity FROM cart WHERE UserId = ? AND ProductId = ?";
					try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
						checkStmt.setInt(1, userId);
						checkStmt.setInt(2, productId);
						try (ResultSet checkRs = checkStmt.executeQuery()) {
							if (checkRs.next()) {
								int cartId = checkRs.getInt("CartId");
								int newQuantity = checkRs.getInt("CartQuantity") + quantity;
								String updateSql = "UPDATE cart SET CartQuantity = ?, AddedAt = ? WHERE CartId = ?";
								try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
									updateStmt.setInt(1, newQuantity);
									updateStmt.setTimestamp(2, new Timestamp(new Date().getTime()));
									updateStmt.setInt(3, cartId);
									return updateStmt.executeUpdate() > 0;
								}
							} else {
								String insertSql = "INSERT INTO cart (UserId, ProductId, CartQuantity, AddedAt) VALUES (?, ?, ?, ?)";
								try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
									insertStmt.setInt(1, userId);
									insertStmt.setInt(2, productId);
									insertStmt.setInt(3, quantity);
									insertStmt.setTimestamp(4, new Timestamp(new Date().getTime()));
									return insertStmt.executeUpdate() > 0;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	// Get cart items for a user
	public List<CartModel> getCartItems(int userId) throws SQLException, ClassNotFoundException {
		List<CartModel> cartItems = new ArrayList<>();
		String sql = "SELECT CartId, UserId, ProductId, CartQuantity, AddedAt FROM cart WHERE UserId = ?";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					CartModel cartItem = new CartModel(rs.getInt("CartId"), rs.getInt("UserId"), rs.getInt("ProductId"),
							rs.getInt("CartQuantity"), rs.getTimestamp("AddedAt"));
					cartItems.add(cartItem);
				}
			}
		}
		return cartItems;
	}

	// Get cart item count for a user
	public int getCartItemCount(int userId) throws SQLException, ClassNotFoundException {
		String sql = "SELECT SUM(CartQuantity) AS TotalItems FROM cart WHERE UserId = ?";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("TotalItems");
				}
			}
		}
		return 0;
	}

	// Get product details for cart items
	public List<ProductModel> getCartProducts(List<CartModel> cartItems) throws SQLException, ClassNotFoundException {
		List<ProductModel> products = new ArrayList<>();
		if (cartItems.isEmpty()) {
			return products;
		}
		StringBuilder sql = new StringBuilder("SELECT ProductId, ProductName, SubcategoryId, BrandId, Gender, "
				+ "ProductMaterial, ProductDescription, ProductPrice, ProductSize, StockQuantity, MainImageUrl, "
				+ "ProductRating, IsFeatured FROM product WHERE ProductId IN (");
		for (int i = 0; i < cartItems.size(); i++) {
			sql.append(i == 0 ? "?" : ", ?");
		}
		sql.append(")");
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
			for (int i = 0; i < cartItems.size(); i++) {
				stmt.setInt(i + 1, cartItems.get(i).getProductId());
			}
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					ProductModel product = new ProductModel(rs.getInt("ProductId"), rs.getString("ProductName"),
							rs.getInt("SubcategoryId"), rs.getInt("BrandId"), rs.getString("Gender"),
							rs.getString("ProductMaterial"), rs.getString("ProductDescription"),
							rs.getDouble("ProductPrice"), rs.getString("ProductSize"), rs.getInt("StockQuantity"),
							rs.getString("MainImageUrl"), rs.getDouble("ProductRating"), rs.getBoolean("IsFeatured"));
					products.add(product);
				}
			}
		}
		return products;
	}

	// Remove a cart item
	public boolean removeFromCart(int cartId) throws SQLException, ClassNotFoundException {
		String sql = "DELETE FROM cart WHERE CartId = ?";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, cartId);
			return stmt.executeUpdate() > 0;
		}
	}

	// Update cart item quantity
	public boolean updateCartQuantity(int cartId, int quantity) throws SQLException, ClassNotFoundException {
		if (quantity <= 0) {
			return false;
		}
		String sql = "SELECT p.StockQuantity, p.ProductId FROM product p JOIN cart c ON p.ProductId = c.ProductId WHERE c.CartId = ?";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, cartId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int stock = rs.getInt("StockQuantity");
					if (stock < quantity) {
						return false;
					}
					String updateSql = "UPDATE cart SET CartQuantity = ?, AddedAt = ? WHERE CartId = ?";
					try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
						updateStmt.setInt(1, quantity);
						updateStmt.setTimestamp(2, new Timestamp(new Date().getTime()));
						updateStmt.setInt(3, cartId);
						return updateStmt.executeUpdate() > 0;
					}
				}
			}
		}
		return false;
	}

	public ProductModel getProductById(int productId) throws SQLException, ClassNotFoundException {
		String sql = "SELECT ProductId, ProductName, ProductPrice, StockQuantity FROM product WHERE ProductId = ?";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, productId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					ProductModel product = new ProductModel();
					product.setProductId(rs.getInt("ProductId"));
					product.setProductName(rs.getString("ProductName"));
					product.setProductPrice(rs.getDouble("ProductPrice"));
					product.setStockQuantity(rs.getInt("StockQuantity"));
					return product;
				}
			}
		}
		return null; // Product not found
	}

	// Clear all cart items for a user
	public boolean clearCart(int userId) throws SQLException, ClassNotFoundException {
		String sql = "DELETE FROM cart WHERE UserId = ?";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			return stmt.executeUpdate() > 0;
		}
	}
}