package com.fdvmlab.foodbyfoodiesforfoodies;

import android.util.Patterns;

public class Util {
    /**
     * Validate email address
     *
     * @param emailAddress
     * @return true if is valid or false otherwise
     */
    public static boolean isEmailValid(String emailAddress) {
        return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }
}
