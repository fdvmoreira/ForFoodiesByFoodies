package com.fdvmlab.forfoodiesbyfoodies.models;

import android.net.Uri;

public class Restaurant extends FoodPlace {
    private String restaurantId;

    public Restaurant(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Restaurant(String name, String postCode, String phoneNumber, String photoUrl, Address address, Rating rating, Uri photo, String restaurantId) {
        super(name, postCode, phoneNumber, photoUrl, address, rating, photo);
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
