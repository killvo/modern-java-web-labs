package com.example.java_labs.controller.command.impl.ticket;

import com.example.java_labs.controller.command.Command;
import com.example.java_labs.controller.command.impl.hall.ShowCreateHallPageCommand;
import com.example.java_labs.controller.exception.IncorrectBuyTicketRequestException;
import com.example.java_labs.controller.services.TicketService;
import com.example.java_labs.controller.exception.IncorrectSessionUserException;
import com.example.java_labs.controller.util.constants.ViewJsp;
import com.example.java_labs.domain.Ticket;
import com.example.java_labs.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public class BuyTicketCommand implements Command {
    private final Logger logger = LoggerFactory.getLogger(BuyTicketCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new IncorrectSessionUserException();
        }

        String expositionId = request.getParameter("expositionId");
        if (expositionId == null) {
            throw new IncorrectBuyTicketRequestException();
        }

        TicketService.getInstance().buyTicket(user.getId(), UUID.fromString(expositionId));

        List<Ticket> tickets = TicketService.getInstance().getTickets(user.getId());
        request.getSession().setAttribute("tickets", tickets);
        logger.info("The user " + user.getUsername() + " has successfully purchased ticket on exposition " + expositionId);
        return ViewJsp.TICKETS.getPath();
    }
}
