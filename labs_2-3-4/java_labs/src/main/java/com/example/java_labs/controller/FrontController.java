package com.example.java_labs.controller;

import com.example.java_labs.controller.command.Command;
import com.example.java_labs.controller.command.constants.Commands;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FrontController", urlPatterns = {"/command/*"})
public class FrontController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        String commandParameter = request.getParameter("command");

        Command command = getCommand(commandParameter);
        String pagePath = command.execute(request, response);

        try {
            request.getRequestDispatcher(pagePath).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private Command getCommand(String commandParameter) {
        return Commands.valueOf(commandParameter).getCommand();
    }

}
