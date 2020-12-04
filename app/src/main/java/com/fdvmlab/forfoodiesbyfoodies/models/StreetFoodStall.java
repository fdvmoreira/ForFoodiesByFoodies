package com.fdvmlab.forfoodiesbyfoodies.models;

public class StreetFoodStall extends FoodPlace {
    private String streetFoodStallId;

    public StreetFoodStall(String streetFoodStallId) {
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
