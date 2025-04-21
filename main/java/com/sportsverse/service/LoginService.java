package com.sportsverse.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sportsverse.config.DbConfig;
import com.sportsverse.model.CustomerModel;
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

	public Boolean loginUser(CustomerModel customerModel) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT username, password FROM customer WHERE username = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, customerModel.getUsername());
			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				return validatePassword(result, customerModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return false;
	}

	private boolean validatePassword(ResultSet result, CustomerModel customerModel) throws SQLException {
		String dbUsername = result.getString("username");
		String dbPassword = result.getString("password");

		String decryptedPassword = PasswordUtil.decrypt(dbPassword, dbUsername);

		if (decryptedPassword == null) {
			System.out.println("Decryption failed for user: " + dbUsername);
			return false;
		}

		return dbUsername.equals(customerModel.getUsername()) && decryptedPassword.equals(customerModel.getPassword());
	}

}
