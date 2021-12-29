package com.example.java_labs.controller.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("Користувач з таким логіном уже існує");
    }
}
