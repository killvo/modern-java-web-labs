package com.example.java_labs.controller.exception;

public class IncorrectCreateHalRequestException extends RuntimeException {
    public IncorrectCreateHalRequestException() {
        super("Дані, які ви надіслали для створення залу не валідні");
    }
}
