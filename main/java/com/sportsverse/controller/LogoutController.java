package com.sportsverse.controller;

import java.io.IOException;

import com.sportsverse.util.CookiesUtil;
import com.sportsverse.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles logout by clearing session and role cookies.
 * @author 
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/logout" })
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Remove "role" cookie and invalidate session
		CookiesUtil.deleteCookie(resp, "role");
		SessionUtil.invalidateSession(req);

		// Redirect to login page
		resp.sendRedirect(req.getContextPath() + "/login");
	}
}
