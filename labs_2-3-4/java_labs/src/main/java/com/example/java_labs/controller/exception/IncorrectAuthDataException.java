package com.example.java_labs.controller.exception;

public class IncorrectAuthDataException extends RuntimeException {
    public IncorrectAuthDataException() {
        super("Дані, які ви надіслали для авторизації не валідні");
    }
}
