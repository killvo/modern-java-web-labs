package com.example.java_labs.controller.command.impl.hall;

import com.example.java_labs.controller.command.Command;
import com.example.java_labs.controller.command.impl.authorization.RegisterCommand;
import com.example.java_labs.controller.exception.IncorrectCreateHalRequestException;
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

public class CreateHallCommand implements Command {
    private final Logger logger = LoggerFactory.getLogger(CreateHallCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new IncorrectSessionUserException();
        }
        String name = request.getParameter("name");
        String floor = request.getParameter("floor");
        String number = request.getParameter("number");

        if (name == null || floor == null || number == null) {
            throw new IncorrectCreateHalRequestException();
        }

        HallService.getInstance().createHall(user.getId(), name, Integer.parseInt(floor), Integer.parseInt(number));

        List<Hall> halls = HallService.getInstance().getAllHalls(user.getId());

        request.getSession().setAttribute("halls", halls);
        logger.info("Successful created hall " + name + " by " + user.getUsername());
        return ViewJsp.ADMIN_CREATE_HALL.getPath();
    }
}
