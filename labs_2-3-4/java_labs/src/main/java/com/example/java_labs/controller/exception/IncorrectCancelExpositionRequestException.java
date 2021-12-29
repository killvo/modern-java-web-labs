package com.example.java_labs.controller.exception;

public class IncorrectCancelExpositionRequestException extends RuntimeException {
    public IncorrectCancelExpositionRequestException() {
        super("Дані скасування експозиції не валідні.");
    }
}
