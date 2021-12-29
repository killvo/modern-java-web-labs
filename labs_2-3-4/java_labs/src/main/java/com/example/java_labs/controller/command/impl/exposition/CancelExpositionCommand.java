package com.example.java_labs.controller.command.impl.exposition;

import com.example.java_labs.controller.command.Command;
import com.example.java_labs.controller.command.impl.ticket.ShowTicketsCommand;
import com.example.java_labs.controller.exception.IncorrectCancelExpositionRequestException;
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
import java.util.UUID;

public class CancelExpositionCommand implements Command {
    private final Logger logger = LoggerFactory.getLogger(CancelExpositionCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new IncorrectSessionUserException();
        }
        String expositionId = request.getParameter("expositionId");
        if (expositionId == null) {
            throw new IncorrectCancelExpositionRequestException();
        }

        ExpositionService.getInstance().cancelExposition(user.getId(), UUID.fromString(expositionId));
        List<Exposition> expositions = ExpositionService.getInstance().getExpositionsForAdmin(user.getId());

        request.getSession().setAttribute("expositions", expositions);
        logger.info("The user " + user.getUsername() + " has successfully cancelled exposition " + expositionId);
        return ViewJsp.INDEX_JSP.getPath();
    }
}
