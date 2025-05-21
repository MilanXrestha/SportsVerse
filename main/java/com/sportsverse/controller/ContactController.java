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

@WebServlet("/contact")
public class ContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("activePage", "contact");
		req.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String subject = req.getParameter("subject");
		String message = req.getParameter("message");

		// Basic server-side validation
		if (name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty() || subject == null
				|| subject.trim().isEmpty() || message == null || message.trim().isEmpty()) {
			req.setAttribute("errorMessage", "All fields are required.");
			
			req.setAttribute("activePage", "contact");
			req.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(req, resp);
			return;
		}

		// Save to database
		String sql = "INSERT INTO contact_inquiry (name, email, subject, message) VALUES (?, ?, ?, ?)";
		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, subject);
			stmt.setString(4, message);
			stmt.executeUpdate();
			req.setAttribute("successMessage", "Thank you for your message! We'll get back to you soon.");
		} catch (SQLException | ClassNotFoundException e) {
			req.setAttribute("errorMessage", "An error occurred while submitting your message. Please try again.");
			e.printStackTrace();
		}

		req.setAttribute("activePage", "contact");
		req.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(req, resp);
	}
}