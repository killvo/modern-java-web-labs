package com.example.java_labs.controller.command.impl.authorization;

import com.example.java_labs.controller.command.Command;
import com.example.java_labs.controller.util.constants.ViewJsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutCommand implements Command {
    private final Logger logger = LoggerFactory.getLogger(LogOutCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        logger.info("Successful logout");
        return ViewJsp.INDEX_JSP.getPath();
    }
}
