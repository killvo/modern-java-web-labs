package com.example.java_labs.controller.exception;

public class ExpositionNotFoundException extends RuntimeException {
    public ExpositionNotFoundException() {
        super("Експозиція не знайдена.");
    }
}
