package com.example.java_labs.controller.exception;

public class IncorrectBuyTicketRequestException extends RuntimeException {
    public IncorrectBuyTicketRequestException() {
        super("Дані покупки квитка не валідні.");
    }
}
