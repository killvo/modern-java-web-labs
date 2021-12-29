package com.example.java_labs.controller.command.impl.exposition;

import com.example.java_labs.controller.command.Command;
import com.example.java_labs.controller.exception.IncorrectCreateExpositionRequestException;
import com.example.java_labs.controller.exception.IncorrectSessionUserException;
import com.example.java_labs.controller.services.ExpositionService;
import com.example.java_labs.controller.util.constants.ViewJsp;
import com.example.java_labs.domain.Exposition;
import com.example.java_labs.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CreateExpositionCommand implements Command {
    private final Logger logger = LoggerFactory.getLogger(CreateExpositionCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new IncorrectSessionUserException();
        }
        String topic = request.getParameter("topic");
        String price = request.getParameter("price");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String[] selectedHalls = request.getParameterValues("selectedHalls");

        if (topic == null ||
                price == null ||
                fromDate == null ||
                toDate == null ||
                startTime == null ||
                endTime == null ||
                selectedHalls == null
        ) {
            throw new IncorrectCreateExpositionRequestException();
        }

        Date fromDateParsed = null;
        Date toDateParsed = null;
        try {
            toDateParsed = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
            fromDateParsed = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Exposition exposition = ExpositionService.getInstance().createExposition(user.getId(), topic, Double.valueOf(price),
                fromDateParsed, toDateParsed, startTime, endTime, selectedHalls);

        List<Exposition> expositions = ExpositionService.getInstance().getExpositionsForAdmin(user.getId());

        request.getSession().setAttribute("expositions", expositions);
        logger.info("The user " + user.getUsername() + " has successfully created exposition " + exposition.getTopic());
        return ViewJsp.INDEX_JSP.getPath();
    }
}
