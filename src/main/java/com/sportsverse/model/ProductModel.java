package com.sportsverse.model;

/**
 * ProductModel represents a product in the SportsVerse system. It contains
 * product-related properties and provides getter and setter methods.
 */
public class ProductModel {

    // Unique identifier for each product (auto-generated in the database)
    private int productId;

    private String productName;
    private int subcategoryId;
    private int brandId;
    private String gender;
    private String productMaterial;
    private String productDescription;
    private double productPrice;
    private String productSize;
    private int stockQuantity;
    private String mainImageUrl;
    private double productRating;
    private boolean featured;
    private int wishlistId;

    // Default constructor. Initializes an empty ProductModel object.
    public ProductModel() {
    }

    /**
     * Parameterized constructor. Creates a ProductModel object with all fields
     * initialized.
     */
    public ProductModel(int productId, String productName, int subcategoryId, int brandId, String gender,
            String productMaterial, String productDescription, double productPrice, String productSize,
            int stockQuantity, String mainImageUrl, double productRating, boolean featured) {
        this.productId = productId;
        this.productName = productName;
        this.subcategoryId = subcategoryId;
        this.brandId = brandId;
        this.gender = gender;
        this.productMaterial = productMaterial;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productSize = productSize;
        this.stockQuantity = stockQuantity;
        this.mainImageUrl = mainImageUrl;
        this.productRating = productRating;
        this.featured = featured;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProductMaterial() {
        return productMaterial;
    }

    public void setProductMaterial(String productMaterial) {
        this.productMaterial = productMaterial;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public double getProductRating() {
        return productRating;
    }

    public void setProductRating(double productRating) {
        this.productRating = productRating;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }
    
    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }
}