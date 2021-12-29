package com.example.java_labs.controller.exception;

public class IncorrectShowExpositionDetailsRequestException extends RuntimeException {
    public IncorrectShowExpositionDetailsRequestException() {
        super("Дані для отримання деталей експозиції не правильні.");
    }
}
