package com.example.java_labs.controller.exception;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
        super("Неправильний пароль.");
    }
}
