package com.fdvmlab.forfoodiesbyfoodies.models;

public class Rating {
    private float numberOfStarts;
    private String ratingId;

    public Rating(float numberOfStarts) {
        this.numberOfStarts = numberOfStarts;
    }

    public Rating() {
    }

    public float getNumberOfStarts() {
        return numberOfStarts;
    }

    public void setNumberOfStarts(float numberOfStarts) {
        this.numberOfStarts = numberOfStarts;
    }

    public Rating(float numberOfStarts, String ratingId) {
        this.numberOfStarts = numberOfStarts;
        this.ratingId = ratingId;
    }


}
