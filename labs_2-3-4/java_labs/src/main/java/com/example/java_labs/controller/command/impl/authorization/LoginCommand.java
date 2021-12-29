package com.example.java_labs.controller.command.impl.authorization;

import com.example.java_labs.controller.command.Command;
import com.example.java_labs.controller.command.utils.AuthUtils;
import com.example.java_labs.controller.services.UserService;
import com.example.java_labs.controller.util.constants.ViewJsp;
import com.example.java_labs.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {
    private final Logger logger = LoggerFactory.getLogger(LoginCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        AuthUtils.throwIfInvalidData(username, password);

        User user = UserService.getInstance().authorizeUser(username, password);

        request.getSession().invalidate();
        request.getSession().setAttribute("user", user);
        logger.info("Successful login for " + username);
        return ViewJsp.INDEX_JSP.getPath();
    }
}
