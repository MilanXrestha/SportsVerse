package com.sportsverse.model;

import java.util.Date;

/**
 * CartModel represents a cart item for a user in SportsVerse.
 * It links a user with products in their cart, including quantity and the date they were added.
 */
public class CartModel {

	// Unique identifier for the cart item
	private int cartId;

	// The ID of the user who owns the cart item
	private int userId;

	// The ID of the product added to the cart
	private int productId;

	// Quantity of the product added to the cart
	private int cartQuantity;

	// Date and time when the product was added to the cart
	private Date addedAt;

	// Default constructor
	public CartModel() {
	}

	/**
	 * Parameterized constructor to initialize all fields.
	 */
	public CartModel(int cartId, int userId, int productId, int cartQuantity, Date addedAt) {
		this.cartId = cartId;
		this.userId = userId;
		this.productId = productId;
		this.cartQuantity = cartQuantity;
		this.addedAt = addedAt;
	}

	// Getters and Setters
	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCartQuantity() {
		return cartQuantity;
	}

	public void setCartQuantity(int cartQuantity) {
		this.cartQuantity = cartQuantity;
	}

	public Date getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Date addedAt) {
		this.addedAt = addedAt;
	}
}
