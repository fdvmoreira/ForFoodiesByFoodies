package com.fdvmlab.forfoodiesbyfoodies.models;

public class FoodPlace {
    private String name, postCode, phoneNumber, photoUrl;
    private Address address;

    public FoodPlace(String name, Address address, String phoneNumber, String photoUrl) {
        this.name = name;
        this.postCode = postCode;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
    }

    public FoodPlace() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
