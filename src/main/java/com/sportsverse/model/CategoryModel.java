package com.sportsverse.model;

/**
 * CategoryModel represents a product category in SportsVerse.
 * Each product belongs to a subcategory, which is linked to a category.
 */
public class CategoryModel {

	// Unique identifier for the category
	private int categoryId;

	// Name of the category (e.g., "Clothing", "Equipment")
	private String categoryName;

	// Optional image path or URL for the category
	private String categoryImage;

	// Default constructor
	public CategoryModel() {
	}

	/**
	 * Parameterized constructor to initialize all fields.
	 */
	public CategoryModel(int categoryId, String categoryName, String categoryImage) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryImage = categoryImage;
	}

	// Getters and Setters
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryImage() {
		return categoryImage;
	}

	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}
}
