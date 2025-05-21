package com.sportsverse.model;

import java.util.Date;

/**
 * PaymentModel represents a payment record in SportsVerse.
 * It includes details like method, amount, date, and status.
 */
public class PaymentModel {

	// Unique identifier for the payment
	private int paymentId;

	// Foreign key referencing the related order
	private int orderId;

	// Foreign key referencing the selected payment method
	private int paymentMethodId;

	// The amount paid
	private double paymentAmount;

	// Date and time when the payment was made
	private Date paymentDate;

	// Status of the payment (e.g., "Paid", "Pending", "Failed")
	private String paymentStatus;

	// Default constructor
	public PaymentModel() {
	}

	/**
	 * Parameterized constructor to initialize all fields.
	 */
	public PaymentModel(int paymentId, int orderId, int paymentMethodId, double paymentAmount, Date paymentDate,
			String paymentStatus) {
		this.paymentId = paymentId;
		this.orderId = orderId;
		this.paymentMethodId = paymentMethodId;
		this.paymentAmount = paymentAmount;
		this.paymentDate = paymentDate;
		this.paymentStatus = paymentStatus;
	}

	// Getters and Setters
	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(int paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
}
