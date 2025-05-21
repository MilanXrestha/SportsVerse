package com.sportsverse.service;

import com.sportsverse.config.DbConfig;
import com.sportsverse.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private final CartService cartService = new CartService();

    public List<AddressModel> getUserAddresses(int userId) throws SQLException, ClassNotFoundException {
        List<AddressModel> addresses = new ArrayList<>();
        String sql = "SELECT AddressId, UserId, StreetAddress, City, NepalState, AddressType FROM address WHERE UserId = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AddressModel address = new AddressModel(
                        rs.getInt("AddressId"),
                        rs.getInt("UserId"),
                        rs.getString("StreetAddress"),
                        rs.getString("City"),
                        rs.getString("NepalState"),
                        rs.getString("AddressType")
                    );
                    addresses.add(address);
                }
            }
        }
        return addresses;
    }

    public boolean addAddress(AddressModel address) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO address (UserId, StreetAddress, City, NepalState, AddressType) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, address.getUserId());
            stmt.setString(2, address.getStreetAddress());
            stmt.setString(3, address.getCity());
            stmt.setString(4, address.getNepalState());
            stmt.setString(5, address.getAddressType());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateAddress(AddressModel address) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE address SET StreetAddress = ?, City = ?, NepalState = ?, AddressType = ? WHERE AddressId = ? AND UserId = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, address.getStreetAddress());
            stmt.setString(2, address.getCity());
            stmt.setString(3, address.getNepalState());
            stmt.setString(4, address.getAddressType());
            stmt.setInt(5, address.getAddressId());
            stmt.setInt(6, address.getUserId());
            return stmt.executeUpdate() > 0;
        }
    }

    public List<PaymentMethodModel> getAllPaymentMethods() throws SQLException, ClassNotFoundException {
        List<PaymentMethodModel> methods = new ArrayList<>();
        String sql = "SELECT PaymentMethodId, MethodName FROM paymentmethod";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PaymentMethodModel method = new PaymentMethodModel(
                    rs.getInt("PaymentMethodId"),
                    rs.getString("MethodName")
                );
                methods.add(method);
            }
        }
        return methods;
    }

    public int createOrder(OrderModel order) throws SQLException, ClassNotFoundException {
        Connection conn = DbConfig.getDbConnection();
        try {
            conn.setAutoCommit(false);

            String orderSql = "INSERT INTO orders (UserId, OrderDate, OrderStatus, TotalAmount, AddressId, PaymentMethodId, OrderPaymentStatus) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(orderSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, order.getUserId());
                stmt.setTimestamp(2, new Timestamp(order.getOrderDate().getTime()));
                stmt.setString(3, order.getOrderStatus());
                stmt.setDouble(4, order.getTotalAmount());
                stmt.setInt(5, order.getAddressId());
                stmt.setInt(6, order.getPaymentMethodId());
                stmt.setString(7, order.getOrderPaymentStatus());
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int orderId = rs.getInt(1);
                        String productSql = "INSERT INTO orderproduct (OrderId, ProductId, QuantityOrdered, UnitPrice) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement productStmt = conn.prepareStatement(productSql)) {
                            for (OrderProductModel product : order.getOrderProducts()) {
                                productStmt.setInt(1, orderId);
                                productStmt.setInt(2, product.getProductId());
                                productStmt.setInt(3, product.getQuantityOrdered());
                                productStmt.setDouble(4, product.getUnitPrice());
                                productStmt.addBatch();
                            }
                            productStmt.executeBatch();
                        }
                        String stockSql = "UPDATE product SET StockQuantity = StockQuantity - ? WHERE ProductId = ? AND StockQuantity >= ?";
                        try (PreparedStatement stockStmt = conn.prepareStatement(stockSql)) {
                            for (OrderProductModel product : order.getOrderProducts()) {
                                stockStmt.setInt(1, product.getQuantityOrdered());
                                stockStmt.setInt(2, product.getProductId());
                                stockStmt.setInt(3, product.getQuantityOrdered());
                                stockStmt.addBatch();
                            }
                            int[] results = stockStmt.executeBatch();
                            for (int result : results) {
                                if (result <= 0) {
                                    conn.rollback();
                                    return -1; // Stock insufficient
                                }
                            }
                        }
                        String paymentSql = "INSERT INTO payment (OrderId, PaymentMethodId, PaymentAmount, PaymentDate, PaymentStatus) " +
                                          "VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement paymentStmt = conn.prepareStatement(paymentSql)) {
                            paymentStmt.setInt(1, orderId);
                            paymentStmt.setInt(2, order.getPaymentMethodId());
                            paymentStmt.setDouble(3, order.getTotalAmount());
                            paymentStmt.setTimestamp(4, new Timestamp(order.getOrderDate().getTime()));
                            paymentStmt.setString(5, order.getOrderPaymentStatus());
                            paymentStmt.executeUpdate();
                        }
                        cartService.clearCart(order.getUserId());
                        conn.commit();
                        return orderId;
                    }
                }
            }
            conn.rollback();
            return -1;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public List<OrderModel> getUserOrders(int userId) throws SQLException, ClassNotFoundException {
        List<OrderModel> orders = new ArrayList<>();
        String sql = "SELECT OrderId, UserId, OrderDate, OrderStatus, TotalAmount, AddressId, PaymentMethodId, OrderPaymentStatus " +
                     "FROM orders WHERE UserId = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderModel order = new OrderModel();
                    order.setOrderId(rs.getInt("OrderId"));
                    order.setUserId(rs.getInt("UserId"));
                    order.setOrderDate(rs.getTimestamp("OrderDate"));
                    order.setOrderStatus(rs.getString("OrderStatus"));
                    order.setTotalAmount(rs.getDouble("TotalAmount"));
                    order.setAddressId(rs.getInt("AddressId"));
                    order.setPaymentMethodId(rs.getInt("PaymentMethodId"));
                    order.setOrderPaymentStatus(rs.getString("OrderPaymentStatus"));
                    order.setOrderProducts(getOrderProducts(order.getOrderId(), order));
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    private List<OrderProductModel> getOrderProducts(int orderId, OrderModel order) throws SQLException, ClassNotFoundException {
        List<OrderProductModel> products = new ArrayList<>();
        Map<Integer, String> productNames = new HashMap<>();
        String sql = "SELECT op.OrderId, op.ProductId, op.QuantityOrdered, op.UnitPrice, p.ProductName " +
                     "FROM orderproduct op JOIN product p ON op.ProductId = p.ProductId WHERE op.OrderId = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderProductModel product = new OrderProductModel();
                    product.setOrderId(rs.getInt("OrderId"));
                    product.setProductId(rs.getInt("ProductId"));
                    product.setQuantityOrdered(rs.getInt("QuantityOrdered"));
                    product.setUnitPrice(rs.getDouble("UnitPrice"));
                    products.add(product);
                    productNames.put(product.getProductId(), rs.getString("ProductName"));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getOrderProducts: " + e.getMessage());
            throw e;
        }
        order.setProductNames(productNames);
        return products;
    }
}