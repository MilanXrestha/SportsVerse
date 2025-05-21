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
    private static final String ADMIN_DASHBOARD = "/dashboard";

    // Public pages (accessible to all)
    private static final String[] PUBLIC_PAGES = {
        LOGIN, REGISTER, HOME, ROOT,
        "/products", "/about", "/services", "/faq"
    };

    // Restricted pages (only logged-in users)
    private static final String[] RESTRICTED_PAGES = {
        "/cart", "/checkout",
        "/profile", "/profile/verify", "/profile/update", "/profile/delete"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No init needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        // Check if user is logged in by verifying username in session
        boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;
        String role = (String) SessionUtil.getAttribute(req, "role");

        // Allow static resources
        if (path.matches(".*\\.(css|js|png|jpg|jpeg|gif|ico|webp)$")) {
            chain.doFilter(request, response);
            return;
        }

        // Admin redirection from home
        if (isLoggedIn && "admin".equals(role) && (path.equals(HOME) || path.equals(ROOT))) {
            res.sendRedirect(contextPath + ADMIN_DASHBOARD);
            return;
        }

        // Admin-only dashboard access
        if (path.equals(ADMIN_DASHBOARD) && (!isLoggedIn || !"admin".equals(role))) {
            res.sendRedirect(contextPath + HOME);
            return;
        }

        // Allow access to public pages
        for (String publicPath : PUBLIC_PAGES) {
            if (path.equals(publicPath)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // Restrict certain pages to logged-in users
        for (String restrictedPath : RESTRICTED_PAGES) {
            if (path.equals(restrictedPath)) {
                if (!isLoggedIn) {
                    res.sendRedirect(contextPath + LOGIN);
                    return;
                }
            }
        }

        // Allow all other requests for logged-in users
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Nothing to destroy
    }
}