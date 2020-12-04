package com.fdvmlab.forfoodiesbyfoodies;

import android.util.Patterns;

public class Util {
    /**
     * Validate email address
     *
     * @param emailAddress email address
     * @return true if is valid or false otherwise
     */
    public static boolean isEmailValid(String emailAddress) {
        return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    /**
     * validate password
     *
     * @param password firebase app password
     * @return true if is valid or false otherwise
     */
    public static boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    /**
     * validate input text
     *
     * @param text plain text
     * @return true if is valid or false otherwise
     */
    public static boolean isTextInvalid(String text) {
        return text.isEmpty();
    }
}