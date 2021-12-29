package com.example.java_labs.controller.command.impl.ticket;

import com.example.java_labs.controller.command.Command;
import com.example.java_labs.controller.exception.IncorrectSessionUserException;
import com.example.java_labs.controller.services.TicketService;
import com.example.java_labs.controller.util.constants.ViewJsp;
import com.example.java_labs.domain.Ticket;
import com.example.java_labs.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowTicketsCommand implements Command {
    private final Logger logger = LoggerFactory.getLogger(ShowTicketsCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new IncorrectSessionUserException();
        }

        List<Ticket> tickets = TicketService.getInstance().getTickets(user.getId());
        request.getSession().setAttribute("tickets", tickets);
        logger.info("Successfully opened tickets page for " + user.getUsername());
        return ViewJsp.TICKETS.getPath();
    }
}
