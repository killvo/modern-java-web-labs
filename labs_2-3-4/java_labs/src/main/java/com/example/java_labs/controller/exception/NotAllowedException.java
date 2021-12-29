package com.example.java_labs.controller.exception;

public class NotAllowedException extends RuntimeException {
    public NotAllowedException() {
        super("Ви не маєте прав для здійснення цієї дії");
    }
}
