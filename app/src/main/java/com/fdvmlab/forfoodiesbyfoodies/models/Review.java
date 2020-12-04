package com.fdvmlab.forfoodiesbyfoodies.models;

public class Review {
    private Rating rating;
    private String reviewId, message;
    private FoodPlace foodPlace;
    private User author;

    public Review() {
    }

    public Review(Rating rating, String reviewId, String message, FoodPlace foodPlace, User author) {
        this.rating = rating;
        this.reviewId = reviewId;
        this.message = message;
        this.foodPlace = foodPlace;
        this.author = author;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FoodPlace getFoodPlace() {
        return foodPlace;
    }

    public void setFoodPlace(FoodPlace foodPlace) {
        this.foodPlace = foodPlace;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
