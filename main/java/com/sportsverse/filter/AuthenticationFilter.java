package com.sportsverse.filter;

import java.io.IOException;

import com.sportsverse.util.SessionUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(asyncSupported = true, urlPatterns = { "/*" })
public class AuthenticationFilter implements Filter {

	private static final String LOGIN = "/login";
	private static final String REGISTER = "/register";
	private static final String HOME = "/home";
	private static final String ROOT = "/";
	private static final String ADMIN_DASHBOARD = "/admin/dashboard";
	private static final String PRODUCTS = "/products"; // Public page
	private static final String BLOG = "/blog"; // Public page
	private static final String ABOUT = "/about"; // Public page
	private static final String SERVICES = "/services"; // Public page
	private static final String FAQ = "/faq"; // Public page
	private static final String CART = "/cart"; // Restricted
	private static final String CHECKOUT = "/checkout"; // Restricted
	private static final String PROFILE = "/profile"; // Restricted

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Cast the request and response to HttpServletRequest and HttpServletResponse
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		// Get the requested URI
		String uri = req.getRequestURI();

		// Allow static resources (CSS, JS, images), public pages, and home/root paths
		// to pass
		if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") || uri.endsWith(".jpg")
				|| uri.endsWith(".jpeg") || uri.endsWith(".gif") || uri.endsWith(".ico") || uri.endsWith(".webp")
				|| uri.endsWith(HOME) || uri.endsWith(ROOT) || uri.endsWith(PRODUCTS) || uri.endsWith(BLOG)
				|| uri.endsWith(ABOUT) || uri.endsWith(SERVICES) || uri.endsWith(FAQ)) {
			chain.doFilter(request, response); // Allow public pages to pass
			return;
		}

		// Get the session and check if user is logged in
		boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;

		// If the user is not logged in, redirect to login page unless accessing public
		// pages
		if (!isLoggedIn) {
			if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
				chain.doFilter(request, response); // Allow login and register pages
			} else {
				// Redirect non-logged in users to the login page for restricted pages
				res.sendRedirect(req.getContextPath() + LOGIN);
			}
		} else {
			// User is logged in, check if they are trying to access restricted admin pages
			String role = (String) SessionUtil.getAttribute(req, "role");

			// Allow access to admin dashboard only if the user is an admin
			if (uri.endsWith(ADMIN_DASHBOARD) && (role == null || !role.equals("admin"))) {
				// If not an admin, redirect to home page
				res.sendRedirect(req.getContextPath() + HOME);
			} else {
				// Allow logged-in users to access all other pages, including restricted ones
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}
}
