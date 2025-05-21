package com.sportsverse.controller;

import java.io.IOException;

import com.sportsverse.model.UserModel;
import com.sportsverse.service.LoginService;
import com.sportsverse.util.CookiesUtil;
import com.sportsverse.util.RedirectionUtil;
import com.sportsverse.util.SessionUtil;
import com.sportsverse.util.ValidationUtil;

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
            UserModel user = new UserModel();
            user.setUsername(username);
            user.setPassword(password);

            boolean loginSuccess = loginService.loginUser(user);

            if (loginSuccess) {
                SessionUtil.setAttribute(req, "userId", user.getUserId());
                SessionUtil.setAttribute(req, "username", username);
                SessionUtil.setAttribute(req, "role", user.getRole());

                if (rememberMe) {
                    CookiesUtil.addCookie(resp, "username", username, 60 * 60 * 24 * 7); // 7 days
                } else {
                    CookiesUtil.deleteCookie(resp, "username");
                }

                CookiesUtil.addCookie(resp, "role", user.getRole(), 60 * 60); // 1 hour

                if ("admin".equalsIgnoreCase(user.getRole())) {
                    resp.sendRedirect(req.getContextPath() + "/dashboard");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/home");
                }
            } else {
                handleLoginFailure(req, resp, username);
            }
        } else {
            redirectionUtil.setMsgAndRedirect(req, resp, "error", "Please fill all the fields!", RedirectionUtil.AUTH_PAGE);
        }
    }

    private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, String attemptedUsername) throws ServletException, IOException {
        req.setAttribute("error", "Username or password incorrect!");
        req.setAttribute("savedUsername", attemptedUsername);
        req.getRequestDispatcher(RedirectionUtil.AUTH_PAGE).forward(req, resp);
    }
}