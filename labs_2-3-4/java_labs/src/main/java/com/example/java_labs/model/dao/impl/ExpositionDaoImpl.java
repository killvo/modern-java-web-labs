package com.example.java_labs.model.dao.impl;

import com.example.java_labs.domain.Exposition;
import com.example.java_labs.domain.Schedule;
import com.example.java_labs.model.dao.ExpositionDAO;
import com.example.java_labs.model.dao.connection.impl.ConnectionFactoryImpl;
import com.example.java_labs.model.dto.ExpositionStatistics;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ExpositionDaoImpl implements ExpositionDAO {
    private static ExpositionDAO expositionDaoImpl;
    private static final String SQL_SELECT_ALL_EXPOSITIONS = "SELECT e.*, s.from_date, s.to_date, s.start_time, s.end_time " +
            "FROM expositions e " +
            "INNER JOIN schedules s ON e.schedule_id = s.id " +
            "WHERE e.cancelled = false " +
            "ORDER BY e.topic ASC";
    private static final String SQL_SELECT_STATISTICS = "SELECT DISTINCT e.*, s.from_date, s.to_date, s.start_time, s.end_time, COUNT(t.*) AS tickets_count " +
            "FROM expositions e " +
            "LEFT JOIN schedules s ON e.schedule_id = s.id " +
            "LEFT JOIN tickets t ON t.exposition_id = e.id " +
            "WHERE e.cancelled = false " +
            "GROUP BY e.id, s.from_date, s.to_date, s.start_time, s.end_time";
    private static final String SQL_SELECT_EXPOSITIONS_BY_TOPIC = "SELECT e.*, s.from_date, s.to_date, s.start_time, s.end_time " +
            "FROM expositions e " +
            "INNER JOIN schedules s ON e.schedule_id = s.id " +
            "WHERE e.topic = ? AND e.cancelled = false";
    private static final String SQL_SELECT_EXPOSITIONS_BY_PRICE = "SELECT e.*, s.from_date, s.to_date, s.start_time, s.end_time " +
            "FROM expositions e " +
            "INNER JOIN schedules s ON e.schedule_id = s.id " +
            "WHERE e.cancelled = false AND e.price BETWEEN ? AND ? " +
            "ORDER BY e.topic ASC";
    private static final String SQL_SELECT_EXPOSITIONS_BY_DATE = "SELECT e.*, s.from_date, s.to_date, s.start_time, s.end_time " +
            "FROM expositions e " +
            "INNER JOIN schedules s ON e.schedule_id = s.id " +
            "WHERE e.cancelled = false AND ? BETWEEN s.from_date AND s.to_date " +
            "ORDER BY e.topic ASC";
    private static final String SQL_SELECT_EXPOSITION_BY_ID = "SELECT e.*, s.from_date, s.to_date, s.start_time, s.end_time " +
            "FROM expositions e " +
            "INNER JOIN schedules s ON e.schedule_id = s.id " +
            "WHERE e.cancelled = false AND e.id = ?::uuid";
    private static final String SQL_DELETE_EXPOSITION_BY_ID = "DELETE FROM expositions WHERE id = ?::uuid";
    private static final String SQL_CREATE_EXPOSITION = "INSERT INTO expositions (topic, schedule_id, price) " +
            "VALUES(?, ?::uuid, ?)";
    private static final String SQL_UPDATE_EXPOSITION = "UPDATE expositions " +
            "SET cancelled = ?, topic = ?, schedule_id = ?::uuid, price = ? " +
            "WHERE id = ?::uuid";

    private ExpositionDaoImpl() {

    }

    public static ExpositionDAO getInstance() {
        if (expositionDaoImpl == null) {
            expositionDaoImpl = new ExpositionDaoImpl();
        }
        return expositionDaoImpl;
    }

    public List<Exposition> findAll() {
        List<Exposition> expositions = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_EXPOSITIONS);
            while (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                boolean cancelled = resultSet.getBoolean(2);
                String topic = resultSet.getString(3);
                UUID scheduleId = resultSet.getObject(4, UUID.class);
                Double price = resultSet.getDouble(5);
                Date fromDate = resultSet.getDate(6);
                Date toDate = resultSet.getDate(7);
                String startTime = resultSet.getString(8);
                String endTime = resultSet.getString(9);
                Schedule schedule = new Schedule(scheduleId, fromDate, toDate, startTime, endTime);
                expositions.add(new Exposition(id, cancelled, topic, schedule, price));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expositions;
    }

    public List<ExpositionStatistics> selectStatistics() {
        List<ExpositionStatistics> expositions = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_STATISTICS);
            while (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                boolean cancelled = resultSet.getBoolean(2);
                String topic = resultSet.getString(3);
                UUID scheduleId = resultSet.getObject(4, UUID.class);
                Double price = resultSet.getDouble(5);
                Date fromDate = resultSet.getDate(6);
                Date toDate = resultSet.getDate(7);
                String startTime = resultSet.getString(8);
                String endTime = resultSet.getString(9);
                Integer ticketsCount = resultSet.getInt(10);
                Schedule schedule = new Schedule(scheduleId, fromDate, toDate, startTime, endTime);
                expositions.add(new ExpositionStatistics(id, cancelled, topic, schedule, price, ticketsCount));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expositions;
    }

    public List<Exposition> findByTopic(String topic) {
        List<Exposition> expositions = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_EXPOSITIONS_BY_TOPIC)
        ) {
            statement.setString(1, topic);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                boolean cancelled = resultSet.getBoolean(2);
                UUID scheduleId = resultSet.getObject(4, UUID.class);
                Double price = resultSet.getDouble(5);
                Date fromDate = resultSet.getDate(6);
                Date toDate = resultSet.getDate(7);
                String startTime = resultSet.getString(8);
                String endTime = resultSet.getString(9);
                Schedule schedule = new Schedule(scheduleId, fromDate, toDate, startTime, endTime);
                expositions.add(new Exposition(id, cancelled, topic, schedule, price));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expositions;
    }

    public List<Exposition> findByPrice(Double from, Double to) {
        List<Exposition> expositions = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_EXPOSITIONS_BY_PRICE)
        ) {
            statement.setDouble(1, from);
            statement.setDouble(2, to);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                boolean cancelled = resultSet.getBoolean(2);
                String topic = resultSet.getString(3);
                UUID scheduleId = resultSet.getObject(4, UUID.class);
                Double price = resultSet.getDouble(5);
                Date fromDate = resultSet.getDate(6);
                Date toDate = resultSet.getDate(7);
                String startTime = resultSet.getString(8);
                String endTime = resultSet.getString(9);
                Schedule schedule = new Schedule(scheduleId, fromDate, toDate, startTime, endTime);
                expositions.add(new Exposition(id, cancelled, topic, schedule, price));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expositions;
    }

    public List<Exposition> findByDate(Date date) {
        List<Exposition> expositions = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_EXPOSITIONS_BY_DATE)
        ) {
            java.sql.Date dateSql = new java.sql.Date(date.getTime());
            statement.setDate(1, dateSql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                boolean cancelled = resultSet.getBoolean(2);
                String topic = resultSet.getString(3);
                UUID scheduleId = resultSet.getObject(4, UUID.class);
                Double price = resultSet.getDouble(5);
                Date fromDate = resultSet.getDate(6);
                Date toDate = resultSet.getDate(7);
                String startTime = resultSet.getString(8);
                String endTime = resultSet.getString(9);
                Schedule schedule = new Schedule(scheduleId, fromDate, toDate, startTime, endTime);
                expositions.add(new Exposition(id, cancelled, topic, schedule, price));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expositions;
    }

    public Exposition findById(UUID id) {
        Exposition exposition = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_EXPOSITION_BY_ID)
        ) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                boolean cancelled = resultSet.getBoolean(2);
                String topic = resultSet.getString(3);
                UUID scheduleId = resultSet.getObject(4, UUID.class);
                Double price = resultSet.getDouble(5);
                Date fromDate = resultSet.getDate(6);
                Date toDate = resultSet.getDate(7);
                String startTime = resultSet.getString(8);
                String endTime = resultSet.getString(9);
                Schedule schedule = new Schedule(scheduleId, fromDate, toDate, startTime, endTime);
                exposition = new Exposition(id, cancelled, topic, schedule, price);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exposition;
    }

    public boolean delete(UUID id) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_EXPOSITION_BY_ID)
        ) {
            statement.setString(1, id.toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected == 1;
    }

    public boolean delete(Exposition exposition) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_EXPOSITION_BY_ID)
        ) {
            statement.setString(1, exposition.getId().toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected == 1;
    }

    public Exposition create(Exposition entity) {
        Exposition exposition = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_CREATE_EXPOSITION, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.getTopic());
            statement.setString(2, entity.getSchedule().getId().toString());
            statement.setDouble(3, entity.getPrice());

            ResultSet resultSet;
            UUID generatedId = null;
            int rowAffected = statement.executeUpdate();
            if(rowAffected == 1) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedId = (UUID) resultSet.getObject(1);
                }
                if (generatedId != null) {
                    exposition = entity;
                    exposition.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exposition;
    }

    public Exposition update(Exposition entity) {
        Exposition exposition = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_EXPOSITION)
        ) {
            statement.setBoolean(1, entity.isCancelled());
            statement.setString(2, entity.getTopic());
            statement.setString(3, entity.getSchedule().getId().toString());
            statement.setDouble(4, entity.getPrice());
            statement.setString(5, entity.getId().toString());
            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1) {
                exposition = entity;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exposition;
    }
}
