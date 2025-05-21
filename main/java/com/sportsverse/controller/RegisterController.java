package com.sportsverse.controller;

import com.sportsverse.model.UserModel;
import com.sportsverse.model.AddressModel;
import com.sportsverse.service.RegisterService;
import com.sportsverse.util.RedirectionUtil;
import com.sportsverse.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * RegisterController handles user registration. It extracts user and address
 * data from the request, validates it, and uses RegisterService to save the
 * user and address.
 */
@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ValidationUtil validator = new ValidationUtil();
	private final RegisterService registerService = new RegisterService();
	private final RedirectionUtil redirectionUtil = new RedirectionUtil();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		redirectionUtil.redirectToPage(req, resp, RedirectionUtil.AUTH_PAGE);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserModel user = extractUserData(request);
		AddressModel address = extractAddressData(request);
		String sameAddress = request.getParameter("sameAddress");

		if (!validateRegistrationData(user, address, sameAddress, request, response))
			return;

		// Register the user and get the generated user ID
		String status = registerService.registerUserAndAddress(user, address, sameAddress);

		// Redirect with registration status
		redirectionUtil.setMsgAndRedirect(request, response, "status", status, RedirectionUtil.AUTH_PAGE);
	}

	/**
	 * Extracts user data from the registration form.
	 */
	private UserModel extractUserData(HttpServletRequest request) {
		UserModel user = new UserModel();
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setEmail(request.getParameter("email"));
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setPhone(request.getParameter("phone"));
		user.setGender(request.getParameter("gender"));
		user.setContactPreference(request.getParameter("contactMethod"));
		
		// Dynamically assign role
		if (user.getUsername() != null && user.getUsername().toLowerCase().startsWith("admin")) {
		    user.setRole("admin");
		} else {
		    user.setRole("customer");
		}
		return user;
	}
	

	/**
	 * Extracts address data from the registration form.
	 */
	private AddressModel extractAddressData(HttpServletRequest request) {
		AddressModel address = new AddressModel();
		address.setStreetAddress(request.getParameter("streetAddress"));
		address.setCity(request.getParameter("city"));
		address.setNepalState(request.getParameter("nepalState"));
		address.setAddressType("Permanent"); // Default to Permanent
		return address;
	}

	/**
	 * Validates both user and address input fields.
	 */
	private boolean validateRegistrationData(UserModel user, AddressModel address, String sameAddress,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (validator.isNullOrEmpty(user.getFirstName()) || !validator.isAlphabetic(user.getFirstName())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidFirstName",
					RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (validator.isNullOrEmpty(user.getLastName()) || !validator.isAlphabetic(user.getLastName())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidLastName",
					RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (!validator.isValidEmail(user.getEmail())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidEmail", RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (!validator.isAlphanumericStartingWithLetter(user.getUsername())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidUsername",
					RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (!validator.isValidPhoneNumber(user.getPhone())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidPhone", RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (!validator.isValidGender(user.getGender())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidGender", RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (validator.isNullOrEmpty(user.getContactPreference())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidContactMethod",
					RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (validator.isNullOrEmpty(address.getStreetAddress())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidStreetAddress",
					RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (validator.isNullOrEmpty(address.getCity())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidCity", RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (validator.isNullOrEmpty(address.getNepalState())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidState", RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (validator.isNullOrEmpty(sameAddress) || (!sameAddress.equals("yes") && !sameAddress.equals("no"))) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidSameAddress",
					RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (validator.isNullOrEmpty(user.getPassword()) || !validator.isValidPassword(user.getPassword())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidPassword",
					RedirectionUtil.AUTH_PAGE);
			return false;
		}

		return true;
	}
}