package com.sportsverse.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sportsverse.config.DbConfig;
import com.sportsverse.model.UserModel;
import com.sportsverse.util.PasswordUtil;

public class LoginService {

    private Connection dbConn;
    private boolean isConnectionError = false;

    public LoginService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    public boolean loginUser(UserModel userModel) {
        if (isConnectionError) {
            System.err.println("Connection Error!");
            return false;
        }

        String query = "SELECT UserId, Username, Password, Role FROM users WHERE Username = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, userModel.getUsername());
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return validatePassword(result, userModel);
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException {
        int userId = result.getInt("UserId");
        String dbUsername = result.getString("Username");
        String dbPassword = result.getString("Password");
        String role = result.getString("Role");

        String decryptedPassword = PasswordUtil.decrypt(dbPassword, dbUsername);
        if (decryptedPassword == null) {
            System.err.println("Decryption failed for user: " + dbUsername);
            return false;
        }

        if (dbUsername != null && dbPassword != null && dbUsername.equals(userModel.getUsername()) && decryptedPassword.equals(userModel.getPassword())) {
            // Set UserId and Role in UserModel
            userModel.setUserId(userId);
            // Override role if username starts with "admin"
            if (dbUsername.toLowerCase().startsWith("admin")) {
                userModel.setRole("admin");
            } else {
                userModel.setRole(role != null ? role : "customer");
            }
            return true;
        }
        return false;
    }
}