package com.sportsverse.model;

/**
 * OrderProductModel represents the many-to-many relationship between orders and products in SportsVerse.
 * It contains the details of the quantity and unit price of each product in a specific order.
 */
public class OrderProductModel {

	// The ID of the order
	private int orderId;

	// The ID of the product
	private int productId;

	// The quantity of the product in the order
	private int quantityOrdered;

	// The unit price of the product in the order
	private double unitPrice;

	// Default constructor
	public OrderProductModel() {
	}

	/**
	 * Parameterized constructor to initialize all fields.
	 */
	public OrderProductModel(int orderId, int productId, int quantityOrdered, double unitPrice) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantityOrdered = quantityOrdered;
		this.unitPrice = unitPrice;
	}

	// Getters and Setters
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantityOrdered() {
		return quantityOrdered;
	}

	public void setQuantityOrdered(int quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
}
