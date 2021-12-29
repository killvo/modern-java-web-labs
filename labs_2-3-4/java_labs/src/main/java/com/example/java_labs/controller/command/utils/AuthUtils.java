package com.example.java_labs.controller.command.utils;

import com.example.java_labs.controller.exception.IncorrectAuthDataException;

public class AuthUtils {
    public static void throwIfInvalidData(String username, String password) {
        int usernameMinLength = 5;
        int passwordMinLength = 6;
        if (!areUsernameValid(username, usernameMinLength) || !arePasswordValid(password, passwordMinLength)) {
            throw new IncorrectAuthDataException();
        }
    }

    public static boolean areUsernameValid(String username, int minLength) {
        return username != null && username.trim().length() >= minLength;
    }

    public static boolean arePasswordValid(String password, int minLength) {
        return password != null && password.length() >= minLength;
    }
}
