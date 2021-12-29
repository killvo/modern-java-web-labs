package com.example.java_labs.controller.command.impl.exposition;

import com.example.java_labs.controller.command.Command;
import com.example.java_labs.controller.exception.IncorrectSessionUserException;
import com.example.java_labs.controller.services.ExpositionService;
import com.example.java_labs.controller.util.constants.ViewJsp;
import com.example.java_labs.domain.Exposition;
import com.example.java_labs.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAdminExpositionsCommand implements Command {
    private final Logger logger = LoggerFactory.getLogger(ShowAdminExpositionsCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new IncorrectSessionUserException();
        }

        List<Exposition> expositions = ExpositionService.getInstance().getExpositionsForAdmin(user.getId());

        request.getSession().setAttribute("expositions", expositions);
        logger.info("Successfully loaded expositions for admin " + user.getUsername());
        return ViewJsp.INDEX_JSP.getPath();
    }
}
