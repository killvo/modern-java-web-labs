package com.example.java_labs.controller.command.impl.hall;

import com.example.java_labs.controller.command.Command;
import com.example.java_labs.controller.exception.IncorrectSessionUserException;
import com.example.java_labs.controller.services.HallService;
import com.example.java_labs.controller.util.constants.ViewJsp;
import com.example.java_labs.domain.Hall;
import com.example.java_labs.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCreateHallPageCommand implements Command {
    private final Logger logger = LoggerFactory.getLogger(ShowCreateHallPageCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new IncorrectSessionUserException();
        }

        List<Hall> halls = HallService.getInstance().getAllHalls(user.getId());

        request.getSession().setAttribute("halls", halls);
        logger.info("Successfully opened create hall page for " + user.getUsername());
        return ViewJsp.ADMIN_CREATE_HALL.getPath();
    }
}
