package com.example.java_labs.controller.services;

import com.example.java_labs.controller.exception.ExpositionNotFoundException;
import com.example.java_labs.controller.exception.IncorrectBuyTicketRequestException;
import com.example.java_labs.domain.Exposition;
import com.example.java_labs.domain.Ticket;
import com.example.java_labs.domain.User;
import com.example.java_labs.domain.constants.RoleEnum;
import com.example.java_labs.model.dao.ExpositionDAO;
import com.example.java_labs.model.dao.TicketDAO;
import com.example.java_labs.model.dao.UserDAO;
import com.example.java_labs.model.dao.factory.enums.DaoEnum;
import com.example.java_labs.model.dao.factory.impl.DaoFactoryImpl;
import com.example.java_labs.model.dao.impl.ExpositionDaoImpl;
import com.example.java_labs.model.dao.impl.TicketDaoImpl;
import com.example.java_labs.model.dao.impl.UserDaoImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.java_labs.controller.util.ValidationUtils.verifyUserHasRole;
import static com.example.java_labs.controller.util.ValidationUtils.verifyUserNotNull;

public class TicketService {
    private static TicketService ticketService;
    private TicketDAO ticketDAO;
    private UserDAO userDao;
    private ExpositionDAO expositionDAO;

    private TicketService() {
        DaoFactoryImpl factory = DaoFactoryImpl.getInstance();
        ticketDAO = (TicketDaoImpl) factory.getDaoByName(DaoEnum.TICKET_DAO);
        userDao = (UserDaoImpl) factory.getDaoByName(DaoEnum.USER_DAO);
        expositionDAO = (ExpositionDaoImpl) factory.getDaoByName(DaoEnum.EXPOSITION_DAO);
    }

    public static TicketService getInstance() {
        if (ticketService == null) {
            ticketService = new TicketService();
        }
        return ticketService;
    }

    public List<Ticket> getTickets(UUID userId) {
        User user = userDao.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.USER);

        return ticketDAO.findByUserId(userId);
    }

    public Ticket buyTicket(UUID userId, UUID expositionId) {
        User user = userDao.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.USER);
        if (expositionId == null) {
            throw new IncorrectBuyTicketRequestException();
        }

        Exposition exposition = expositionDAO.findById(expositionId);
        if (exposition == null) {
            throw new ExpositionNotFoundException();
        }

        Ticket ticket = new Ticket();
        ticket.setDatetime(LocalDateTime.now());
        ticket.setExposition(exposition);
        ticket.setUser(user);

        return ticketDAO.create(ticket);
    }

}
