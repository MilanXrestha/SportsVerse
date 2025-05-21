package com.sportsverse.controller;

import com.sportsverse.model.AddressModel;
import com.sportsverse.model.UserModel;
import com.sportsverse.service.ProfileService;
import com.sportsverse.util.RedirectionUtil;
import com.sportsverse.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet({ "/profile", "/profile/verify", "/profile/update", "/profile/delete", "/profile/cancel" })
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProfileService profileService = new ProfileService();
    private final RedirectionUtil redirectionUtil = new RedirectionUtil();
    private final ValidationUtil validationUtil = new ValidationUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) com.sportsverse.util.SessionUtil.getAttribute(req, "username");

        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Integer userId = getUserIdByUsername(username);
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            UserModel user = profileService.getUserById(userId);
            if (user == null || "admin".equals(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                return;
            }

            req.setAttribute("user", user);
            req.setAttribute("permanentAddress", profileService.getAddressByType(userId, "Permanent"));
            req.setAttribute("shippingAddress", profileService.getAddressByType(userId, "Shipping"));
            req.setAttribute("verifiedAction", req.getSession().getAttribute("verifiedAction"));

            redirectionUtil.redirectToPage(req, resp, RedirectionUtil.PROFILE_PAGE);
        } catch (SQLException | ClassNotFoundException e) {
            req.setAttribute("errorMessage", "Error fetching profile data.");
            redirectionUtil.redirectToPage(req, resp, RedirectionUtil.PROFILE_PAGE);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) com.sportsverse.util.SessionUtil.getAttribute(req, "username");

        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Integer userId = getUserIdByUsername(username);
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            UserModel user = profileService.getUserById(userId);
            if (user == null || "admin".equals(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                return;
            }

            String path = req.getServletPath();
            if ("/profile/verify".equals(path)) {
                String password = req.getParameter("password");
                String action = req.getParameter("action");

                if (profileService.verifyPassword(userId, password)) {
                    req.getSession().setAttribute("verifiedAction", action);
                    req.setAttribute("verifiedAction", action);
                    doGet(req, resp);
                } else {
                    req.setAttribute("errorMessage", "Invalid password.");
                    doGet(req, resp);
                }
            } else if ("/profile/update".equals(path)) {
                String action = req.getParameter("action");
                String verifiedAction = (String) req.getSession().getAttribute("verifiedAction");

                if (!action.equals(verifiedAction)) {
                    req.setAttribute("errorMessage", "Please verify password first.");
                    doGet(req, resp);
                    return;
                }

                List<String> errors = new ArrayList<>();

                if ("personal".equals(action)) {
                    String firstName = req.getParameter("firstName");
                    String lastName = req.getParameter("lastName");
                    String email = req.getParameter("email");
                    String phone = req.getParameter("phone");
                    String gender = req.getParameter("gender");
                    String contactPreference = req.getParameter("contactPreference");

                    if (!validationUtil.isAlphabetic(firstName)) {
                        errors.add("First name must contain only letters.");
                    }
                    if (!validationUtil.isAlphabetic(lastName)) {
                        errors.add("Last name must contain only letters.");
                    }
                    if (!validationUtil.isValidEmail(email)) {
                        errors.add("Invalid email format.");
                    }
                    if (!validationUtil.isNullOrEmpty(phone) && !validationUtil.isValidPhoneNumber(phone)) {
                        errors.add("Phone number must be 10 digits starting with 97 or 98.");
                    }
                    if (!validationUtil.isValidGender(gender)) {
                        errors.add("Invalid gender selection.");
                    }
                    if (!"Email".equals(contactPreference) && !"Phone".equals(contactPreference)) {
                        errors.add("Invalid contact preference.");
                    }

                    if (errors.isEmpty()) {
                        UserModel updatedUser = new UserModel();
                        updatedUser.setUserId(userId);
                        updatedUser.setFirstName(firstName);
                        updatedUser.setLastName(lastName);
                        updatedUser.setEmail(email);
                        updatedUser.setPhone(phone);
                        updatedUser.setGender(gender);
                        updatedUser.setContactPreference(contactPreference);
                        profileService.updateUser(updatedUser);
                        req.setAttribute("successMessage", "Personal information updated.");
                    } else {
                        req.setAttribute("validationErrors", errors);
                    }
                } else if ("permanent".equals(action) || "shipping".equals(action)) {
                    String streetAddress = req.getParameter("streetAddress");
                    String city = req.getParameter("city");
                    String nepalState = req.getParameter("nepalState");

                    if (validationUtil.isNullOrEmpty(streetAddress)) {
                        errors.add("Street address is required.");
                    }
                    if (validationUtil.isNullOrEmpty(city)) {
                        errors.add("City is required.");
                    }
                    if (validationUtil.isNullOrEmpty(nepalState)) {
                        errors.add("State is required.");
                    }

                    if (errors.isEmpty()) {
                        AddressModel address = new AddressModel();
                        address.setUserId(userId);
                        address.setStreetAddress(streetAddress);
                        address.setCity(city);
                        address.setNepalState(nepalState);
                        address.setAddressType(action.equals("permanent") ? "Permanent" : "Shipping");
                        profileService.updateAddress(address);
                        req.setAttribute("successMessage", action.equals("permanent") ? "Permanent address updated." : "Shipping address updated.");
                    } else {
                        req.setAttribute("validationErrors", errors);
                    }
                } else if ("password".equals(action)) {
                    String newPassword = req.getParameter("newPassword");
                    String confirmPassword = req.getParameter("confirmPassword");

                    if (!validationUtil.isValidPassword(newPassword)) {
                        errors.add("Password must be at least 8 characters with 1 uppercase letter, 1 number, and 1 symbol.");
                    }
                    if (!validationUtil.doPasswordsMatch(newPassword, confirmPassword)) {
                        errors.add("Passwords do not match.");
                    }

                    if (errors.isEmpty()) {
                        profileService.updatePassword(userId, newPassword);
                        req.setAttribute("successMessage", "Password updated.");
                    } else {
                        req.setAttribute("validationErrors", errors);
                    }
                }

                req.getSession().removeAttribute("verifiedAction");
                doGet(req, resp);
            } else if ("/profile/delete".equals(path)) {
                String verifiedAction = (String) req.getSession().getAttribute("verifiedAction");
                if (!"delete".equals(verifiedAction)) {
                    req.setAttribute("errorMessage", "Please verify password first.");
                    doGet(req, resp);
                    return;
                }
                profileService.deleteUser(userId);
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + "/login");
            } else if ("/profile/cancel".equals(path)) {
                req.getSession().removeAttribute("verifiedAction");
                resp.sendRedirect(req.getContextPath() + "/profile");
            }
        } catch (SQLException | ClassNotFoundException e) {
            req.setAttribute("errorMessage", "Error processing request.");
            doGet(req, resp);
        }
    }

    private Integer getUserIdByUsername(String username) {
        try {
            String sql = "SELECT UserId FROM users WHERE Username = ?";
            try (java.sql.Connection conn = com.sportsverse.config.DbConfig.getDbConnection();
                 java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                try (java.sql.ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("UserId");
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}