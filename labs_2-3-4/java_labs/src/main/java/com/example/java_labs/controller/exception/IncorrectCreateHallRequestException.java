package com.example.java_labs.controller.exception;

public class IncorrectCreateHallRequestException extends RuntimeException {
    public IncorrectCreateHallRequestException() {
        super("Дані створення зали не валідні.");
    }
}
