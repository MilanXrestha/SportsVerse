package com.sportsverse.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderModel {
	private int orderId;
	private int userId;
	private Date orderDate;
	private String orderStatus;
	private double totalAmount;
	private int addressId;
	private int paymentMethodId;
	private String orderPaymentStatus;
	private List<OrderProductModel> orderProducts = new ArrayList<>();
	private Map<Integer, String> productNames = new HashMap<>(); // Added for product names

	// Getters and Setters
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public int getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(int paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	public String getOrderPaymentStatus() {
		return orderPaymentStatus;
	}

	public void setOrderPaymentStatus(String orderPaymentStatus) {
		this.orderPaymentStatus = orderPaymentStatus;
	}

	public List<OrderProductModel> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderProductModel> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public Map<Integer, String> getProductNames() {
		return productNames;
	}

	public void setProductNames(Map<Integer, String> productNames) {
		this.productNames = productNames;
	}
}