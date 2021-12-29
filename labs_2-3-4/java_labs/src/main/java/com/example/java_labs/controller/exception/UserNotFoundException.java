package com.example.java_labs.controller.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Користувач не знайдений");
    }
}
