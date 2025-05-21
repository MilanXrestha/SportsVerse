package com.sportsverse.model;

/**
 * BrandModel represents a brand in SportsVerse.
 * Products are associated with brands such as Nike, Adidas, etc.
 */
public class BrandModel {

	// Unique identifier for the brand
	private int brandId;

	// Name of the brand
	private String brandName;

	// Default constructor
	public BrandModel() {
	}

	/**
	 * Parameterized constructor to initialize all fields.
	 */
	public BrandModel(int brandId, String brandName) {
		this.brandId = brandId;
		this.brandName = brandName;
	}

	// Getters and Setters
	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
