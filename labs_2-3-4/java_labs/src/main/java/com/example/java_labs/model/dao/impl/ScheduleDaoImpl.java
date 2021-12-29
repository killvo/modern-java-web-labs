package com.example.java_labs.model.dao.impl;

import com.example.java_labs.domain.Schedule;
import com.example.java_labs.model.dao.ScheduleDAO;
import com.example.java_labs.model.dao.connection.impl.ConnectionFactoryImpl;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class ScheduleDaoImpl implements ScheduleDAO {
    private static ScheduleDAO scheduleDaoImpl;
    private static final String SQL_CREATE_SCHEDULE = "INSERT INTO schedules (from_date, to_date, start_time, end_time) " +
            "VALUES(?, ?, ?, ?)";
    private static final String SQL_UPDATE_SCHEDULE = "UPDATE schedules " +
            "SET from_date = ?, to_date = ?, start_time = ?, end_time = ? " +
            "WHERE id = ?::uuid";

    private ScheduleDaoImpl() {

    }

    public static ScheduleDAO getInstance() {
        if (scheduleDaoImpl == null) {
            scheduleDaoImpl = new ScheduleDaoImpl();
        }
        return scheduleDaoImpl;
    }

    public List<Schedule> findAll() {
        throw new UnsupportedOperationException();
    }

    public Schedule findById(UUID id) {
        throw new UnsupportedOperationException();
    }

    public boolean delete(UUID id) {
        throw new UnsupportedOperationException();
    }

    public boolean delete(Schedule schedule) {
        throw new UnsupportedOperationException();
    }

    public Schedule create(Schedule entity) {
        Schedule schedule = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_CREATE_SCHEDULE, Statement.RETURN_GENERATED_KEYS)
        ) {
            java.sql.Date fromDate = new java.sql.Date(entity.getFromDate().getTime());
            java.sql.Date toDate = new java.sql.Date(entity.getToDate().getTime());
            statement.setDate(1, fromDate);
            statement.setDate(2, toDate);
            statement.setString(3, entity.getStartTime());
            statement.setString(4, entity.getEndTime());

            ResultSet resultSet;
            UUID generatedId = null;
            int rowAffected = statement.executeUpdate();
            if(rowAffected == 1) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedId = (UUID) resultSet.getObject(1);
                }
                if (generatedId != null) {
                    schedule = entity;
                    schedule.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return schedule;
    }

    public Schedule update(Schedule entity) {
        Schedule schedule = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_SCHEDULE)
        ) {
            java.sql.Date fromDate = new java.sql.Date(entity.getFromDate().getTime());
            java.sql.Date toDate = new java.sql.Date(entity.getToDate().getTime());
            statement.setDate(1, fromDate);
            statement.setDate(2, toDate);
            statement.setString(3, entity.getStartTime());
            statement.setString(4, entity.getEndTime());
            statement.setString(5, entity.getId().toString());
            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1) {
                schedule = entity;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return schedule;
    }
}
