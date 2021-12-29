package com.example.java_labs.controller.exception;

public class InvalidIdException extends RuntimeException {
    public InvalidIdException() {
        super("Id не валідний");
    }
}
