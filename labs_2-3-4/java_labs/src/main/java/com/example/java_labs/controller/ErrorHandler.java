package com.example.java_labs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ErrorHandler")
public class ErrorHandler extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processError(request, response);
    }
    private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        request.setAttribute("statusCode", statusCode);
        request.setAttribute("errorMessage", throwable.getMessage());
        try {
            logger.error("Processed error: " + throwable.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}