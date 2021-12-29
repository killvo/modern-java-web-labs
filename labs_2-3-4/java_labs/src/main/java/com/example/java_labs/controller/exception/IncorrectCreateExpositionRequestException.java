package com.example.java_labs.controller.exception;

public class IncorrectCreateExpositionRequestException extends RuntimeException {
    public IncorrectCreateExpositionRequestException() {
        super("Дані створення експозиції не валідні.");
    }
}
