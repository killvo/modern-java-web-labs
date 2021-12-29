package com.example.java_labs.model.dao.impl;

import com.example.java_labs.domain.Exposition;
import com.example.java_labs.domain.ExpositionToHall;
import com.example.java_labs.domain.Hall;
import com.example.java_labs.model.dao.ExpositionToHallDAO;
import com.example.java_labs.model.dao.connection.impl.ConnectionFactoryImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExpositionToHallDaoImpl implements ExpositionToHallDAO {
    private static ExpositionToHallDAO expositionToHallDaoImpl;
    private static final String SQL_SELECT_ALL_EXPOSITIONS_TO_HALLS = "SELECT e2h.*, h.name, h.floor, h.number " +
            "FROM expositions2halls e2h " +
            "INNER JOIN halls h on h.id = e2h.hall_id";
    private static final String SQL_SELECT_EXPOSITIONS_TO_HALLS_BY_EXPOSITION_ID = "SELECT e2h.*, h.name, h.floor, h.number " +
            "FROM expositions2halls e2h " +
            "INNER JOIN halls h on h.id = e2h.hall_id " +
            "WHERE e2h.exposition_id = ?::uuid";
    private static final String SQL_SELECT_EXPOSITIONS_TO_HALLS_BY_ID = "SELECT e2h.*, h.name, h.floor, h.number " +
            "FROM expositions2halls e2h " +
            "INNER JOIN halls h on h.id = e2h.hall_id " +
            "WHERE e2h.id = ?::uuid";
    private static final String SQL_DELETE_EXPOSITIONS_TO_HALLS_BY_ID = "DELETE FROM expositions2halls " +
            "WHERE id = ?::uuid";
    private static final String SQL_CREATE_EXPOSITIONS_TO_HALLS = "INSERT INTO expositions2halls (exposition_id, hall_id) " +
            "VALUES(?::uuid, ?::uuid)";
    private static final String SQL_UPDATE_EXPOSITIONS_TO_HALLS = "UPDATE expositions2halls " +
            "SET exposition_id = ?::uuid, hall_id = ?::uuid " +
            "WHERE id = ?::uuid";

    private ExpositionToHallDaoImpl() {

    }

    public static ExpositionToHallDAO getInstance() {
        if (expositionToHallDaoImpl == null) {
            expositionToHallDaoImpl = new ExpositionToHallDaoImpl();
        }
        return expositionToHallDaoImpl;
    }

    @Override
    public List<ExpositionToHall> findAll() {
        List<ExpositionToHall> expositionToHalls = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_EXPOSITIONS_TO_HALLS);
            while (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                UUID expositionId = resultSet.getObject(2, UUID.class);
                UUID hallId = resultSet.getObject(3, UUID.class);
                String name = resultSet.getString(4);
                Integer floor = resultSet.getInt(5);
                Integer number = resultSet.getInt(6);

                Exposition exposition = new Exposition();
                exposition.setId(expositionId);

                Hall hall = new Hall();
                hall.setId(hallId);
                hall.setName(name);
                hall.setFloor(floor);
                hall.setNumber(number);

                expositionToHalls.add(new ExpositionToHall(id, exposition, hall));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expositionToHalls;
    }

    public List<ExpositionToHall> findByExpositionId(UUID expositionId) {
        List<ExpositionToHall> expositionToHalls = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_EXPOSITIONS_TO_HALLS_BY_EXPOSITION_ID)
        ) {
            statement.setString(1, expositionId.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                UUID hallId = resultSet.getObject(3, UUID.class);
                String name = resultSet.getString(4);
                Integer floor = resultSet.getInt(5);
                Integer number = resultSet.getInt(6);

                Exposition exposition = new Exposition();
                exposition.setId(expositionId);

                Hall hall = new Hall();
                hall.setId(hallId);
                hall.setName(name);
                hall.setFloor(floor);
                hall.setNumber(number);

                expositionToHalls.add(new ExpositionToHall(id, exposition, hall));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expositionToHalls;
    }

    @Override
    public ExpositionToHall findById(UUID id) {
        ExpositionToHall expositionToHall = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_EXPOSITIONS_TO_HALLS_BY_ID)
        ) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UUID expositionId = resultSet.getObject(2, UUID.class);
                UUID hallId = resultSet.getObject(3, UUID.class);
                String name = resultSet.getString(4);
                Integer floor = resultSet.getInt(5);
                Integer number = resultSet.getInt(6);

                Exposition exposition = new Exposition();
                exposition.setId(expositionId);

                Hall hall = new Hall();
                hall.setId(hallId);
                hall.setName(name);
                hall.setFloor(floor);
                hall.setNumber(number);

                expositionToHall = new ExpositionToHall(id, exposition, hall);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expositionToHall;
    }

    @Override
    public boolean delete(UUID id) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_EXPOSITIONS_TO_HALLS_BY_ID)
        ) {
            statement.setString(1, id.toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected == 1;
    }

    @Override
    public boolean delete(ExpositionToHall entity) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_EXPOSITIONS_TO_HALLS_BY_ID)
        ) {
            statement.setString(1, entity.getId().toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected == 1;
    }

    @Override
    public ExpositionToHall create(ExpositionToHall entity) {
        ExpositionToHall expositionToHall = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_CREATE_EXPOSITIONS_TO_HALLS, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.getExposition().getId().toString());
            statement.setString(2, entity.getHall().getId().toString());

            int rowAffected = statement.executeUpdate();
            ResultSet resultSet;
            UUID generatedId = null;
            if(rowAffected == 1) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedId = (UUID) resultSet.getObject(1);
                }
                if (generatedId != null) {
                    expositionToHall = entity;
                    expositionToHall.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expositionToHall;
    }

    @Override
    public ExpositionToHall update(ExpositionToHall entity) {
        ExpositionToHall user2Activity = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_EXPOSITIONS_TO_HALLS)
        ) {
            statement.setString(1, entity.getExposition().getId().toString());
            statement.setString(2, entity.getHall().getId().toString());
            statement.setString(3, entity.getId().toString());
            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1) {
                user2Activity = entity;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user2Activity;
    }

}
