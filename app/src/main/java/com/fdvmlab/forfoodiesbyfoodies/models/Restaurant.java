package com.fdvmlab.forfoodiesbyfoodies.models;

public class Restaurant extends FoodPlace {
    private String restaurantId;

    public Restaurant(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Restaurant(String name, Address address, String phoneNumber, String photoUrl, String restaurantId) {
        super(name, address, phoneNumber, photoUrl);
        this.restaurantId = restaurantId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
