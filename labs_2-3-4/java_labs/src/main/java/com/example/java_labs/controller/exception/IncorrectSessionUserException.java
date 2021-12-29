package com.example.java_labs.controller.exception;

public class IncorrectSessionUserException extends RuntimeException {
    public IncorrectSessionUserException() {
        super("Дані сесії не правильні");
    }
}
