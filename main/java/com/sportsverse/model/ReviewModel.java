package com.sportsverse.model;

import java.util.Date;

/**
 * ReviewModel represents a review for a product in SportsVerse.
 * It links a user with a product and includes a rating, comment, and verification status.
 */
public class ReviewModel {

    // Unique identifier for the review
    private int reviewId;

    // The ID of the product being reviewed
    private int productId;

    // The ID of the user who wrote the review
    private int userId;

    // Rating given by the user (e.g., 1.00 to 5.00)
    private float rating;

    // Comment or feedback provided by the user
    private String reviewComment;

    // Date and time when the review was written
    private Date reviewDate;

    // Indicates whether the review has been verified or not
    private boolean verified;

    // Default constructor
    public ReviewModel() {
    }

    /**
     * Parameterized constructor to initialize all fields.
     */
    public ReviewModel(int reviewId, int productId, int userId, float rating, String reviewComment, Date reviewDate,
            boolean verified) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.userId = userId;
        this.rating = rating;
        this.reviewComment = reviewComment;
        this.reviewDate = reviewDate;
        this.verified = verified;
    }

    // Getters and Setters
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}