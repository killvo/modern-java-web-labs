package com.example.java_labs.controller.exception;

public class ExpositionAlreadyCancelledException extends RuntimeException {
    public ExpositionAlreadyCancelledException() {
        super("Ви намагаєтеся скасувати вже скасовану експозицію.");
    }
}
