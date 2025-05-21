package com.sportsverse.model;

/**
 * ProductImageModel represents an image associated with a product in SportsVerse.
 * It supports storing multiple images per product, with a flag for the main image.
 */
public class ProductImageModel {

	// Unique identifier for each product image
	private int imageId;

	// The ID of the product this image is associated with
	private int productId;

	// URL or path to the image
	private String imageUrl;

	// Indicates whether this image is the main image for the product
	private boolean isMainImage;

	// Default constructor
	public ProductImageModel() {
	}

	/**
	 * Parameterized constructor to initialize all fields.
	 */
	public ProductImageModel(int imageId, int productId, String imageUrl, boolean isMainImage) {
		this.imageId = imageId;
		this.productId = productId;
		this.imageUrl = imageUrl;
		this.isMainImage = isMainImage;
	}

	// Getters and Setters
	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isMainImage() {
		return isMainImage;
	}

	public void setMainImage(boolean isMainImage) {
		this.isMainImage = isMainImage;
	}
}
