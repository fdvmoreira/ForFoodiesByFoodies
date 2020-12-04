package com.fdvmlab.forfoodiesbyfoodies.models;

import java.io.Serializable;

public class User implements Serializable {
    private String userId, name, email, password, profilePhotoUrl;
    private UserRole role;

    public User(String name, String email, String password, String profilePhotoUrl) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public User() {
    }

    public User(String name, String email, String password, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String userId, String name, String email, String password, String profilePhotoUrl, UserRole role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePhotoUrl = profilePhotoUrl;
        this.role = role;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
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
