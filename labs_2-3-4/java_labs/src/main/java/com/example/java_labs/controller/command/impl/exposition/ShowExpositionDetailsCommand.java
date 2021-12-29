package com.example.java_labs.controller.command.impl.exposition;

import com.example.java_labs.controller.command.Command;
import com.example.java_labs.controller.exception.IncorrectSessionUserException;
import com.example.java_labs.controller.exception.IncorrectShowExpositionDetailsRequestException;
import com.example.java_labs.controller.services.ExpositionService;
import com.example.java_labs.controller.util.constants.ViewJsp;
import com.example.java_labs.domain.User;
import com.example.java_labs.model.dto.ExpositionDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class ShowExpositionDetailsCommand implements Command {
    private final Logger logger = LoggerFactory.getLogger(ShowExpositionDetailsCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new IncorrectSessionUserException();
        }

        String expositionId = request.getParameter("expositionId");
        if (expositionId == null) {
            throw new IncorrectShowExpositionDetailsRequestException();
        }

        ExpositionDetails expositionDetails = ExpositionService.getInstance()
                .getExpositionDetails(user.getId(), UUID.fromString(expositionId));

        request.getSession().setAttribute("expositionDetails", expositionDetails);
        logger.info("Successfully opened exposition details for user " + user.getUsername());
        return ViewJsp.EXPOSITION_DETAILS.getPath();
    }
}
