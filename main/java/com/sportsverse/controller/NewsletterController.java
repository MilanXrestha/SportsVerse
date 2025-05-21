package com.sportsverse.controller;

import com.sportsverse.config.DbConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/newsletter")
public class NewsletterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");

		// Basic validation
		if (email == null || email.trim().isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
			req.setAttribute("newsletterMessage", "Please enter a valid email address.");
			req.setAttribute("newsletterSuccess", false);
		} else {
			// Check if email already exists
			String checkSql = "SELECT COUNT(*) FROM newsletter WHERE email = ?";
			try (Connection conn = DbConfig.getDbConnection();
					PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
				checkStmt.setString(1, email);
				try (var rs = checkStmt.executeQuery()) {
					if (rs.next() && rs.getInt(1) > 0) {
						req.setAttribute("newsletterMessage", "This email is already subscribed.");
						req.setAttribute("newsletterSuccess", false);
					} else {
						// Insert new subscriber
						String insertSql = "INSERT INTO newsletter (email) VALUES (?)";
						try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
							insertStmt.setString(1, email);
							insertStmt.executeUpdate();
							req.setAttribute("newsletterMessage", "Thank you for subscribing!");
							req.setAttribute("newsletterSuccess", true);
						}
					}
				}
			} catch (SQLException | ClassNotFoundException e) {
				req.setAttribute("newsletterMessage", "An error occurred. Please try again.");
				req.setAttribute("newsletterSuccess", false);
				e.printStackTrace();
			}
		}

		// Forward back to the current page
		String referer = req.getHeader("Referer");
		if (referer != null && !referer.isEmpty()) {
			resp.sendRedirect(referer);
		} else {
			resp.sendRedirect(req.getContextPath() + "/home");
		}
	}
}