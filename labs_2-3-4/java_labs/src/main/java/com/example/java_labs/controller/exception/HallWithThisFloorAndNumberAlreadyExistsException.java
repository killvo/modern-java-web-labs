package com.example.java_labs.controller.exception;

public class HallWithThisFloorAndNumberAlreadyExistsException extends RuntimeException {
    public HallWithThisFloorAndNumberAlreadyExistsException() {
        super("Зала з таким поверхом та номером уже існує");
    }
}
