package com.sportsverse.model;

import java.util.Date;

/**
 * WishlistModel represents a wishlist item for a user in SportsVerse.
 * It links a user with products they have added to their wishlist, including the date they were added.
 */
public class WishlistModel {

	// Unique identifier for the wishlist item
	private int wishlistId;

	// The ID of the user who owns the wishlist item
	private int userId;

	// The ID of the product added to the wishlist
	private int productId;

	// Date and time when the product was added to the wishlist
	private Date addedAt;

	// Default constructor
	public WishlistModel() {
	}

	/**
	 * Parameterized constructor to initialize all fields.
	 */
	public WishlistModel(int wishlistId, int userId, int productId, Date addedAt) {
		this.wishlistId = wishlistId;
		this.userId = userId;
		this.productId = productId;
		this.addedAt = addedAt;
	}

	// Getters and Setters
	public int getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(int wishlistId) {
		this.wishlistId = wishlistId;
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

	public Date getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Date addedAt) {
		this.addedAt = addedAt;
	}
}
