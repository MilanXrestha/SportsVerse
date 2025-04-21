package com.sportsverse.controller;

import com.sportsverse.model.CustomerModel;
import com.sportsverse.service.RegisterService;
import com.sportsverse.util.RedirectionUtil;
import com.sportsverse.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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

		CustomerModel customer = extractCustomerData(request);

		if (!validateCustomerData(customer, request, response))
			return;

		String status = registerService.registerCustomer(customer);

		redirectionUtil.setMsgAndRedirect(request, response, "status", status, RedirectionUtil.AUTH_PAGE);
	}

	private CustomerModel extractCustomerData(HttpServletRequest request) {
		CustomerModel customer = new CustomerModel();
		customer.setFirstName(request.getParameter("firstName"));
		customer.setLastName(request.getParameter("lastName"));
		customer.setEmail(request.getParameter("email"));
		customer.setUsername(request.getParameter("username"));
		customer.setPhone(request.getParameter("phone"));
		customer.setGender(request.getParameter("gender"));
		customer.setContactMethod(request.getParameter("contactMethod"));
		customer.setStreetAddress(request.getParameter("streetAddress"));
		customer.setCity(request.getParameter("city"));
		customer.setNepalState(request.getParameter("nepalState"));
		customer.setPassword(request.getParameter("password"));
		return customer;
	}

	private boolean validateCustomerData(CustomerModel customer, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (validator.isNullOrEmpty(customer.getFirstName()) || !validator.isAlphabetic(customer.getFirstName())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidFirstName",
					RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (validator.isNullOrEmpty(customer.getLastName()) || !validator.isAlphabetic(customer.getLastName())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidLastName",
					RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (!validator.isValidEmail(customer.getEmail())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidEmail", RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (!validator.isAlphanumericStartingWithLetter(customer.getUsername())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidUsername",
					RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (!validator.isValidPhoneNumber(customer.getPhone())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidPhone", RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (!validator.isValidGender(customer.getGender())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidGender", RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (validator.isNullOrEmpty(customer.getContactMethod())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidContactMethod",
					RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (validator.isNullOrEmpty(customer.getStreetAddress())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidStreetAddress",
					RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (validator.isNullOrEmpty(customer.getCity())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidCity", RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (validator.isNullOrEmpty(customer.getNepalState())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidState", RedirectionUtil.AUTH_PAGE);
			return false;
		}

		if (validator.isNullOrEmpty(customer.getPassword()) || !validator.isValidPassword(customer.getPassword())) {
			redirectionUtil.setMsgAndRedirect(request, response, "status", "invalidPasswordFormat",
					RedirectionUtil.AUTH_PAGE);
			return false;
		}

		return true;
	}
}
