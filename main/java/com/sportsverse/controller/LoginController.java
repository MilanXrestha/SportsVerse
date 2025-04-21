package com.sportsverse.controller;

import java.io.IOException;

import com.sportsverse.model.CustomerModel;
import com.sportsverse.service.LoginService;
import com.sportsverse.util.CookiesUtil;
import com.sportsverse.util.RedirectionUtil;
import com.sportsverse.util.SessionUtil;
import com.sportsverse.util.ValidationUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

@WebServlet(urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ValidationUtil validator;
	private RedirectionUtil redirectionUtil;
	private LoginService loginService;

	@Override
	public void init() throws ServletException {
		validator = new ValidationUtil();
		redirectionUtil = new RedirectionUtil();
		loginService = new LoginService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Auto-fill username from cookie if present
		Cookie savedUsernameCookie = CookiesUtil.getCookie(req, "username");
		if (savedUsernameCookie != null) {
			req.setAttribute("savedUsername", savedUsernameCookie.getValue());
		}

		req.getRequestDispatcher(RedirectionUtil.AUTH_PAGE).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		boolean rememberMe = req.getParameter("remember") != null;

		if (!validator.isNullOrEmpty(username) && !validator.isNullOrEmpty(password)) {
			CustomerModel customer = new CustomerModel();
			customer.setUsername(username);
			customer.setPassword(password);

			// Check if it's the admin user by hardcoding the credentials
			if ("admin".equals(username) && "admin".equals(password)) {
				// Admin login logic
				SessionUtil.setAttribute(req, "username", username);
				CookiesUtil.addCookie(resp, "role", "admin", 60 * 60); // Admin role for 1 hour

				// Remember me logic
				if (rememberMe) {
					CookiesUtil.addCookie(resp, "username", username, 60 * 60 * 24 * 7); // 7 days
				} else {
					CookiesUtil.deleteCookie(resp, "username");
				}

				// Redirect to admin dashboard page (using RequestDispatcher for /WEB-INF)
				RequestDispatcher dispatcher = req.getRequestDispatcher(RedirectionUtil.ADMIN_DASHBOARD);
				dispatcher.forward(req, resp);  // Forward to the admin dashboard page
				
			} else {
				// Regular user login validation
				Boolean loginSuccess = loginService.loginUser(customer);

				if (loginSuccess != null && loginSuccess) {
					SessionUtil.setAttribute(req, "username", username);

					// Remember Me logic for regular users
					if (rememberMe) {
						CookiesUtil.addCookie(resp, "username", username, 60 * 60 * 24 * 7); // 7 days
					} else {
						CookiesUtil.deleteCookie(resp, "username");
					}

					// Assign customer role
					CookiesUtil.addCookie(resp, "role", "customer", 60 * 60);
					resp.sendRedirect(req.getContextPath() + "/home");
				} else {
					handleLoginFailure(req, resp, loginSuccess, username);
				}
			}
		} else {
			redirectionUtil.setMsgAndRedirect(req, resp, "error", "Please fill all the fields!",
					RedirectionUtil.AUTH_PAGE);
		}
	}

	private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, Boolean loginStatus,
			String attemptedUsername) throws ServletException, IOException {
		String errorMessage = (loginStatus == null) ? "Server error. Please try again later!"
				: "Username or password incorrect!";
		req.setAttribute("error", errorMessage);
		req.setAttribute("savedUsername", attemptedUsername);
		req.getRequestDispatcher(RedirectionUtil.AUTH_PAGE).forward(req, resp);
	}
}
