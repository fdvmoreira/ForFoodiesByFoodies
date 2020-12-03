package com.fdvmlab.foodbyfoodiesforfoodies.models;

public class User {
    private String userId, name, email, password, profilePhotoUrl;

    public User(String name, String email, String password, String profilePhotoUrl) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public User() {
    }

    public User(String userId, String name, String email, String password, String profilePhotoUrl) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }
}
