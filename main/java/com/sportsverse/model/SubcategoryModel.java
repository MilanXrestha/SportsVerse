package com.sportsverse.model;

/**
 * SubcategoryModel represents a subcategory within a category in SportsVerse.
 * Each subcategory is linked to a parent category.
 */
public class SubcategoryModel {

	// Unique identifier for the subcategory
	private int subcategoryId;

	// The ID of the parent category this subcategory belongs to
	private int categoryId;

	// Name of the subcategory (e.g., "Shoes", "Bats", "Jerseys")
	private String subcategoryName;

	// Default constructor
	public SubcategoryModel() {
	}

	/**
	 * Parameterized constructor to initialize all fields.
	 */
	public SubcategoryModel(int subcategoryId, int categoryId, String subcategoryName) {
		this.subcategoryId = subcategoryId;
		this.categoryId = categoryId;
		this.subcategoryName = subcategoryName;
	}

	// Getters and Setters
	public int getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(int subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubcategoryName() {
		return subcategoryName;
	}

	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}
}
