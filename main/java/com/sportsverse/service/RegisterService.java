package com.sportsverse.service;

import com.sportsverse.config.DbConfig;
import com.sportsverse.model.CustomerModel;
import com.sportsverse.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterService {

    public String registerCustomer(CustomerModel customer) {
        try (Connection con = DbConfig.getDbConnection()) {

            if (isEmailExists(con, customer.getEmail())) {
                return "emailExists";
            }

            if (isUsernameExists(con, customer.getUsername())) {
                return "usernameExists";
            }

            String encryptedPassword = PasswordUtil.encrypt(customer.getUsername(), customer.getPassword());
            if (encryptedPassword == null) {
                return "encryptionError";
            }

            String insertQuery = """
                    INSERT INTO customer
                    (FirstName, LastName, Email, Username, Phone, Gender, ContactMethod, StreetAddress, City, NepalState, Password)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;

            try (PreparedStatement pst = con.prepareStatement(insertQuery)) {
                pst.setString(1, customer.getFirstName());
                pst.setString(2, customer.getLastName());
                pst.setString(3, customer.getEmail());
                pst.setString(4, customer.getUsername());
                pst.setString(5, customer.getPhone());
                pst.setString(6, customer.getGender());
                pst.setString(7, customer.getContactMethod());
                pst.setString(8, customer.getStreetAddress());
                pst.setString(9, customer.getCity());
                pst.setString(10, customer.getNepalState());
                pst.setString(11, encryptedPassword);

                int rowCount = pst.executeUpdate();
                return rowCount > 0 ? "success" : "failed";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    private boolean isEmailExists(Connection con, String email) throws Exception {
        String query = "SELECT 1 FROM customer WHERE Email = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    private boolean isUsernameExists(Connection con, String username) throws Exception {
        String query = "SELECT 1 FROM customer WHERE Username = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }
}
