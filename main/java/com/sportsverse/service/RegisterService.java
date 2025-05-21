package com.sportsverse.service;

import com.sportsverse.config.DbConfig;
import com.sportsverse.model.UserModel;
import com.sportsverse.model.AddressModel;
import com.sportsverse.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * RegisterService handles user and address registration.
 * It ensures that emails and usernames are unique,
 * encrypts the password, and inserts user and address data into the database.
 */
public class RegisterService {

    /**
     * Registers a user and their address(es).
     *
     * @param user        the user data to be inserted
     * @param address     the address data to be linked to the user
     * @param sameAddress indicates if shipping address is same as permanent ("yes" or "no")
     * @return a string status representing the result of the operation
     */
    public String registerUserAndAddress(UserModel user, AddressModel address, String sameAddress) {
        try (Connection con = DbConfig.getDbConnection()) {
            // Check for existing email or username
            if (isEmailExists(con, user.getEmail())) {
                return "emailExists";
            }

            if (isUsernameExists(con, user.getUsername())) {
                return "usernameExists";
            }

            // Encrypt password
            String encryptedPassword = PasswordUtil.encrypt(user.getUsername(), user.getPassword());
            if (encryptedPassword == null) {
                return "encryptionError";
            }

            // Insert into User table and retrieve generated UserId
            String insertUserQuery = """
                INSERT INTO Users (FirstName, LastName, Email, Username, Password, Phone, Gender, ContactPreference, Role)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

            int generatedUserId = -1;

            try (PreparedStatement userPst = con.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS)) {
                userPst.setString(1, user.getFirstName());
                userPst.setString(2, user.getLastName());
                userPst.setString(3, user.getEmail());
                userPst.setString(4, user.getUsername());
                userPst.setString(5, encryptedPassword);
                userPst.setString(6, user.getPhone());
                userPst.setString(7, user.getGender());
                userPst.setString(8, user.getContactPreference());
                userPst.setString(9, user.getRole());

                int userRow = userPst.executeUpdate();

                if (userRow == 0) {
                    return "failed";
                }

                try (ResultSet generatedKeys = userPst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedUserId = generatedKeys.getInt(1);
                    } else {
                        return "userIdNotGenerated";
                    }
                }
            }

            // Insert permanent address
            String insertAddressQuery = """
                INSERT INTO Address (UserId, StreetAddress, City, NepalState, AddressType)
                VALUES (?, ?, ?, ?, ?)
            """;

            try (PreparedStatement addrPst = con.prepareStatement(insertAddressQuery)) {
                addrPst.setInt(1, generatedUserId);
                addrPst.setString(2, address.getStreetAddress());
                addrPst.setString(3, address.getCity());
                addrPst.setString(4, address.getNepalState());
                addrPst.setString(5, "Permanent");

                int addrRow = addrPst.executeUpdate();
                if (addrRow == 0) {
                    return "addressFailed";
                }
            }

            // If sameAddress is "yes", insert a second address record with AddressType "Shipping"
            if ("yes".equals(sameAddress)) {
                try (PreparedStatement addrPst = con.prepareStatement(insertAddressQuery)) {
                    addrPst.setInt(1, generatedUserId);
                    addrPst.setString(2, address.getStreetAddress());
                    addrPst.setString(3, address.getCity());
                    addrPst.setString(4, address.getNepalState());
                    addrPst.setString(5, "Shipping");

                    int addrRow = addrPst.executeUpdate();
                    if (addrRow == 0) {
                        return "addressFailed";
                    }
                }
            }

            return "success";

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    private boolean isEmailExists(Connection con, String email) throws Exception {
        String query = "SELECT 1 FROM Users WHERE Email = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    private boolean isUsernameExists(Connection con, String username) throws Exception {
        String query = "SELECT 1 FROM Users WHERE Username = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }
}