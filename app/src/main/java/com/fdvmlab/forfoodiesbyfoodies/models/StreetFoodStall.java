package com.fdvmlab.forfoodiesbyfoodies.models;

import android.net.Uri;

public class StreetFoodStall extends FoodPlace {
    private String streetFoodStallId;

    public StreetFoodStall(String streetFoodStallId) {
        this.streetFoodStallId = streetFoodStallId;
    }

    public StreetFoodStall(String name, String postCode, String phoneNumber, String photoUrl, Address address, Rating rating, Uri photo, String streetFoodStallId) {
        super(name, postCode, phoneNumber, photoUrl, address, rating, photo);
        this.streetFoodStallId = streetFoodStallId;
    }

    public StreetFoodStall(String name, Address address, String phoneNumber, String photoUrl, String streetFoodStallId) {
        super(name, address, phoneNumber, photoUrl);
        this.streetFoodStallId = streetFoodStallId;
    }

    public String getStreetFoodStallId() {
        return streetFoodStallId;
    }

    public void setStreetFoodStallId(String streetFoodStallId) {
        this.streetFoodStallId = streetFoodStallId;
    }
}
