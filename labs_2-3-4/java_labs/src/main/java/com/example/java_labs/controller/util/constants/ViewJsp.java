package com.example.java_labs.controller.util.constants;

public enum ViewJsp {
    REGISTER_JSP("/register.jsp"),
    LOGIN_JSP("/login.jsp"),
    INDEX_JSP("/index.jsp"),
    ADMIN_EXPOSITIONS_JSP("/admin_expositions.jsp"),
    ADMIN_CREATE_EXPOSITION("/admin_create_exposition.jsp"),
    ADMIN_CREATE_HALL("/admin_create_hall.jsp"),
    EXPOSITION_DETAILS("/exposition_details.jsp"),
    STATISTICS_JSP("/statistics.jsp"),
    TICKETS("/tickets.jsp");

    private String path;

    ViewJsp(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
