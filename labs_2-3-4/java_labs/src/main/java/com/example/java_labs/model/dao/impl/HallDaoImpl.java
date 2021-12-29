package com.example.java_labs.model.dao.impl;

import com.example.java_labs.domain.Exposition;
import com.example.java_labs.domain.Hall;
import com.example.java_labs.model.dao.HallDAO;
import com.example.java_labs.model.dao.connection.impl.ConnectionFactoryImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HallDaoImpl implements HallDAO {
    private static HallDAO hallDaoImpl;
    private static final String SQL_SELECT_ALL_HALLS = "SELECT * " +
            "FROM halls h " +
            "ORDER BY h.name ASC";
    private static final String SQL_SELECT_HALL_BY_ID = "SELECT * " +
            "FROM halls h " +
            "WHERE h.id = ?::uuid";
    private static final String SQL_SELECT_HALL_BY_FLOOR_AND_HALL_NUMBER = "SELECT * " +
            "FROM halls h " +
            "WHERE h.floor = ? AND h.number = ?";
    private static final String SQL_CREATE_HALL = "INSERT INTO halls (name, floor, number) " +
            "VALUES(?, ?, ?)";


    private HallDaoImpl() {

    }

    public static HallDAO getInstance() {
        if (hallDaoImpl == null) {
            hallDaoImpl = new HallDaoImpl();
        }
        return hallDaoImpl;
    }

    public List<Hall> findAll() {
        List<Hall> halls = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_HALLS);
            while (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                String name = resultSet.getString(2);
                Integer floor = resultSet.getInt(3);
                Integer number = resultSet.getInt(4);
                halls.add(new Hall(id, name, floor, number));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return halls;
    }

    public Hall findById(UUID id) {
        Hall hall = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_HALL_BY_ID)
        ) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                Integer floor = resultSet.getInt(3);
                Integer number = resultSet.getInt(4);
                hall = new Hall(id, name, floor, number);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return hall;
    }

    public Hall findByFloorAndHallNumber(Integer floor, Integer hallNumber) {
        Hall hall = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_HALL_BY_FLOOR_AND_HALL_NUMBER)
        ) {
            statement.setInt(1, floor);
            statement.setInt(2, hallNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                String name = resultSet.getString(2);
                hall = new Hall(id, name, floor, hallNumber);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return hall;
    }

    public boolean delete(UUID id) {
        throw new UnsupportedOperationException();
    }

    public boolean delete(Hall entity) {
        throw new UnsupportedOperationException();
    }

    public Hall create(Hall entity) {
        Hall hall = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_CREATE_HALL, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getFloor());
            statement.setInt(3, entity.getNumber());

            ResultSet resultSet;
            UUID generatedId = null;
            int rowAffected = statement.executeUpdate();
            if(rowAffected == 1) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedId = (UUID) resultSet.getObject(1);
                }
                if (generatedId != null) {
                    hall = entity;
                    hall.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return hall;
    }

    public Hall update(Hall entity) {
        throw new UnsupportedOperationException();
    }
}
