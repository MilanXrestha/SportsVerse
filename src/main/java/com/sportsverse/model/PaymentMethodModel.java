package com.sportsverse.model;

/**
 * PaymentMethodModel represents the payment methods available in the SportsVerse.
 * It contains details like payment method ID and name.
 */
public class PaymentMethodModel {

	// Unique identifier for each payment method
	private int paymentMethodId;

	// Name of the payment method (e.g., "Credit Card", "eSewa", "Cash on Delivery")
	private String methodName;

	// Default constructor
	public PaymentMethodModel() {
	}

	/**
	 * Parameterized constructor to initialize a PaymentMethodModel object
	 * with all fields.
	 */
	public PaymentMethodModel(int paymentMethodId, String methodName) {
		this.paymentMethodId = paymentMethodId;
		this.methodName = methodName;
	}

	// Getters and Setters
	public int getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(int paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
