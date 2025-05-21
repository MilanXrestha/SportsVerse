package com.sportsverse.service;

import com.sportsverse.config.DbConfig;
import com.sportsverse.model.ProductModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishlistService {

    public List<ProductModel> getWishlistProducts(int userId) throws SQLException, ClassNotFoundException {
        List<ProductModel> wishlistProducts = new ArrayList<>();
        String sql = "SELECT w.WishlistId, p.ProductId, p.ProductName, p.SubcategoryId, p.BrandId, p.Gender, "
                + "p.ProductMaterial, p.ProductDescription, p.ProductPrice, p.ProductSize, p.StockQuantity, "
                + "p.MainImageUrl, p.ProductRating, p.IsFeatured "
                + "FROM wishlist w "
                + "JOIN product p ON w.ProductId = p.ProductId "
                + "WHERE w.UserId = ? "
                + "ORDER BY w.AddedAt DESC";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductModel product = new ProductModel(
                            rs.getInt("ProductId"),
                            rs.getString("ProductName"),
                            rs.getInt("SubcategoryId"),
                            rs.getInt("BrandId"),
                            rs.getString("Gender"),
                            rs.getString("ProductMaterial"),
                            rs.getString("ProductDescription"),
                            rs.getDouble("ProductPrice"),
                            rs.getString("ProductSize"),
                            rs.getInt("StockQuantity"),
                            rs.getString("MainImageUrl"),
                            rs.getDouble("ProductRating"),
                            rs.getBoolean("IsFeatured")
                    );
                    product.setWishlistId(rs.getInt("WishlistId"));
                    wishlistProducts.add(product);
                }
            }
        }
        return wishlistProducts;
    }

    public void addToWishlist(int userId, int productId) throws SQLException, ClassNotFoundException {
        String checkSql = "SELECT WishlistId FROM wishlist WHERE UserId = ? AND ProductId = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, productId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    return; // Product already in wishlist
                }
            }
        }

        String insertSql = "INSERT INTO wishlist (UserId, ProductId, AddedAt) VALUES (?, ?, NOW())";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        }
    }

    public void removeFromWishlist(int wishlistId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM wishlist WHERE WishlistId = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, wishlistId);
            stmt.executeUpdate();
        }
    }

    public Map<Integer, Integer> getWishlistStatus(int userId, List<Integer> productIds) throws SQLException, ClassNotFoundException {
        Map<Integer, Integer> status = new HashMap<>();
        if (productIds.isEmpty()) {
            return status;
        }
        String sql = "SELECT WishlistId, ProductId FROM wishlist WHERE UserId = ? AND ProductId IN (";
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < productIds.size(); i++) {
            placeholders.append(i == 0 ? "?" : ",?");
        }
        sql += placeholders.toString() + ")";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            for (int i = 0; i < productIds.size(); i++) {
                stmt.setInt(i + 2, productIds.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    status.put(rs.getInt("ProductId"), rs.getInt("WishlistId"));
                }
            }
        }
        return status;
    }
}