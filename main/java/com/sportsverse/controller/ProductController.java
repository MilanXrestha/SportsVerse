package com.sportsverse.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.sportsverse.util.RedirectionUtil;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("/products")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final RedirectionUtil redirectionUtil = new RedirectionUtil();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		redirectionUtil.redirectToPage(req, resp, RedirectionUtil.PRODUCT_PAGE);
	}




}
