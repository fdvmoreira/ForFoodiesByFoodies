package com.fdvmlab.forfoodiesbyfoodies.models;

public class Address {
    private int houseNumber;
    private String addressId, streetName, postCode, city;

    public Address() {
    }

    public Address(int houseNumber, String streetName, String postCode, String city) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.postCode = postCode;
        this.city = city;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
