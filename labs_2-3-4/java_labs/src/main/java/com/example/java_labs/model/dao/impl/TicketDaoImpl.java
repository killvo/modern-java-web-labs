package com.example.java_labs.model.dao.impl;

import com.example.java_labs.domain.Exposition;
import com.example.java_labs.domain.Ticket;
import com.example.java_labs.domain.User;
import com.example.java_labs.model.dao.TicketDAO;
import com.example.java_labs.model.dao.connection.impl.ConnectionFactoryImpl;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketDaoImpl implements TicketDAO {
    private static TicketDAO ticketDaoImpl;
    private static final String SQL_SELECT_ALL_TICKETS = "SELECT t.*, e.topic, e.price " +
            "FROM tickets t " +
            "INNER JOIN expositions e on e.id = t.exposition_id " +
            "ORDER BY t.datetime ASC";
    private static final String SQL_SELECT_TICKETS_BY_USER_ID = "SELECT t.*, e.topic, e.price " +
            "FROM tickets t " +
            "INNER JOIN expositions e on e.id = t.exposition_id " +
            "WHERE t.user_id = ?::uuid " +
            "ORDER BY t.datetime ASC";
    private static final String SQL_SELECT_TICKET_BY_ID = "SELECT t.*, e.topic, e.price " +
            "FROM tickets t " +
            "INNER JOIN expositions e on e.id = t.exposition_id " +
            "WHERE t.id = ?::uuid";
    private static final String SQL_DELETE_TICKET_BY_ID = "DELETE FROM tickets WHERE id = ?::uuid";
    private static final String SQL_CREATE_TICKET = "INSERT INTO tickets (datetime, user_id, exposition_id) " +
            "VALUES(?, ?::uuid, ?::uuid)";

    private TicketDaoImpl() {

    }

    public static TicketDAO getInstance() {
        if (ticketDaoImpl == null) {
            ticketDaoImpl = new TicketDaoImpl();
        }
        return ticketDaoImpl;
    }

    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_TICKETS);
            while (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                LocalDateTime dateTime = resultSet.getObject(2, LocalDateTime.class);
                UUID userId = resultSet.getObject(3, UUID.class);
                UUID expositionId = resultSet.getObject(4, UUID.class);
                String expositionTopic = resultSet.getString(5);
                Double expositionPrice = resultSet.getDouble(6);

                User user = new User();
                user.setId(userId);

                Exposition exposition = new Exposition();
                exposition.setId(expositionId);
                exposition.setTopic(expositionTopic);
                exposition.setPrice(expositionPrice);

                tickets.add(new Ticket(id, dateTime, user, exposition));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tickets;
    }

    public List<Ticket> findByUserId(UUID userId) {
        List<Ticket> tickets = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TICKETS_BY_USER_ID)
        ) {
            statement.setString(1, userId.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                LocalDateTime dateTime = resultSet.getObject(2, LocalDateTime.class);
                UUID expositionId = resultSet.getObject(4, UUID.class);
                String expositionTopic = resultSet.getString(5);
                Double expositionPrice = resultSet.getDouble(6);

                User user = new User();
                user.setId(userId);

                Exposition exposition = new Exposition();
                exposition.setId(expositionId);
                exposition.setTopic(expositionTopic);
                exposition.setPrice(expositionPrice);

                tickets.add(new Ticket(id, dateTime, user, exposition));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tickets;
    }

    public Ticket findById(UUID id) {
        Ticket ticket = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TICKET_BY_ID)
        ) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                LocalDateTime dateTime = resultSet.getObject(2, LocalDateTime.class);
                UUID userId = resultSet.getObject(3, UUID.class);
                UUID expositionId = resultSet.getObject(4, UUID.class);
                String expositionTopic = resultSet.getString(5);
                Double expositionPrice = resultSet.getDouble(6);

                User user = new User();
                user.setId(userId);

                Exposition exposition = new Exposition();
                exposition.setId(expositionId);
                exposition.setTopic(expositionTopic);
                exposition.setPrice(expositionPrice);

                ticket = new Ticket(id, dateTime, user, exposition);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ticket;
    }

    public boolean delete(UUID id) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TICKET_BY_ID)
        ) {
            statement.setString(1, id.toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected == 1;
    }

    public boolean delete(Ticket ticket) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TICKET_BY_ID)
        ) {
            statement.setString(1, ticket.getId().toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected == 1;
    }

    public Ticket create(Ticket entity) {
        Ticket ticket = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_CREATE_TICKET, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setTimestamp(1, Timestamp.valueOf(entity.getDatetime()));
            statement.setString(2, entity.getUser().getId().toString());
            statement.setString(3, entity.getExposition().getId().toString());

            ResultSet resultSet;
            UUID generatedId = null;
            int rowAffected = statement.executeUpdate();
            if(rowAffected == 1) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedId = (UUID) resultSet.getObject(1);
                }
                if (generatedId != null) {
                    ticket = entity;
                    ticket.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ticket;
    }

    public Ticket update(Ticket entity) {
        throw new UnsupportedOperationException();
    }
}
