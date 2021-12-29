package com.example.java_labs.controller.command.impl.exposition;

import com.example.java_labs.controller.command.Command;
import com.example.java_labs.controller.exception.IncorrectSessionUserException;
import com.example.java_labs.controller.services.ExpositionService;
import com.example.java_labs.controller.util.constants.ViewJsp;
import com.example.java_labs.domain.User;
import com.example.java_labs.model.dto.ExpositionStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowStatisticsCommand implements Command {
    private final Logger logger = LoggerFactory.getLogger(ShowStatisticsCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new IncorrectSessionUserException();
        }

        List<ExpositionStatistics> statistics = ExpositionService.getInstance().getStatisticsForAdmin(user.getId());

        request.getSession().setAttribute("statistics", statistics);
        logger.info("Successfully opened statistics page for user " + user.getUsername());
        return ViewJsp.STATISTICS_JSP.getPath();
    }
}
