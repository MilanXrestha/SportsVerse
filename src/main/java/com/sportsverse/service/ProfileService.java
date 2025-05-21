package com.sportsverse.service;

import com.sportsverse.config.DbConfig;
import com.sportsverse.model.AddressModel;
import com.sportsverse.model.UserModel;
import com.sportsverse.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfileService {

    public UserModel getUserById(int userId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM users WHERE UserId = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UserModel(
                        rs.getInt("UserId"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Phone"),
                        rs.getString("Gender"),
                        rs.getString("ContactPreference"),
                        rs.getString("Role")
                    );
                }
            }
        }
        return null;
    }

    public List<AddressModel> getAddressesByUserId(int userId) throws SQLException, ClassNotFoundException {
        List<AddressModel> addresses = new ArrayList<>();
        String sql = "SELECT * FROM address WHERE UserId = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    addresses.add(new AddressModel(
                        rs.getInt("AddressId"),
                        rs.getInt("UserId"),
                        rs.getString("StreetAddress"),
                        rs.getString("City"),
                        rs.getString("NepalState"),
                        rs.getString("AddressType")
                    ));
                }
            }
        }
        return addresses;
    }

    public AddressModel getAddressByType(int userId, String addressType) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM address WHERE UserId = ? AND AddressType = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, addressType);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new AddressModel(
                        rs.getInt("AddressId"),
                        rs.getInt("UserId"),
                        rs.getString("StreetAddress"),
                        rs.getString("City"),
                        rs.getString("NepalState"),
                        rs.getString("AddressType")
                    );
                }
            }
        }
        return new AddressModel(); // Return empty model if no address exists
    }

    public boolean verifyPassword(int userId, String password) throws SQLException, ClassNotFoundException {
        String sql = "SELECT Username, Password FROM users WHERE UserId = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("Username");
                    String encryptedPassword = rs.getString("Password");
                    String decryptedPassword = PasswordUtil.decrypt(encryptedPassword, username);
                    return password.equals(decryptedPassword);
                }
            }
        }
        return false;
    }

    public void updateUser(UserModel user) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE users SET FirstName = ?, LastName = ?, Email = ?, Phone = ?, Gender = ?, ContactPreference = ? WHERE UserId = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getContactPreference());
            stmt.setInt(7, user.getUserId());
            stmt.executeUpdate();
        }
    }

    public void updateAddress(AddressModel address) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO address (UserId, StreetAddress, City, NepalState, AddressType) " +
                     "VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE " +
                     "StreetAddress = ?, City = ?, NepalState = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, address.getUserId());
            stmt.setString(2, address.getStreetAddress());
            stmt.setString(3, address.getCity());
            stmt.setString(4, address.getNepalState());
            stmt.setString(5, address.getAddressType());
            stmt.setString(6, address.getStreetAddress());
            stmt.setString(7, address.getCity());
            stmt.setString(8, address.getNepalState());
            stmt.executeUpdate();
        }
    }

    public void updatePassword(int userId, String newPassword) throws SQLException, ClassNotFoundException {
        String sql = "SELECT Username FROM users WHERE UserId = ?";
        String username;
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    username = rs.getString("Username");
                } else {
                    throw new SQLException("User not found");
                }
            }
        }

        String encryptedPassword = PasswordUtil.encrypt(username, newPassword);
        if (encryptedPassword == null) {
            throw new SQLException("Password encryption failed");
        }

        String updateSql = "UPDATE users SET Password = ? WHERE UserId = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSql)) {
            stmt.setString(1, encryptedPassword);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    public void deleteUser(int userId) throws SQLException, ClassNotFoundException {
        try (Connection conn = DbConfig.getDbConnection()) {
            // Delete addresses
            String deleteAddressSql = "DELETE FROM address WHERE UserId = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteAddressSql)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
            // Delete user
            String deleteUserSql = "DELETE FROM users WHERE UserId = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteUserSql)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
        }
    }
}