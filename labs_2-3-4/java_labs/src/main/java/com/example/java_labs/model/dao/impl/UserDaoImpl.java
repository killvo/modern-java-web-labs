package com.example.java_labs.model.dao.impl;

import com.example.java_labs.domain.Role;
import com.example.java_labs.domain.User;
import com.example.java_labs.model.dao.UserDAO;
import com.example.java_labs.model.dao.connection.impl.ConnectionFactoryImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDaoImpl implements UserDAO {
    private static UserDAO userDaoImpl;
    private static final String SQL_SELECT_ALL_USERS = "SELECT u.*, r.name " +
            "FROM users u " +
            "INNER JOIN roles r ON u.role_id = r.id " +
            "ORDER BY u.username ASC";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT u.*, r.name " +
            "FROM users u " +
            "INNER JOIN roles r ON u.role_id = r.id " +
            "WHERE u.id = ?::uuid";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?::uuid";
    private static final String SQL_CREATE_USER = "INSERT INTO users (username, salt, hash, role_id) " +
            "VALUES(?, ?, ?, ?::uuid)";
    private static final String SQL_UPDATE_USER = "UPDATE users " +
            "SET username = ?, salt = ?, hash = ?, role_id = ?::uuid " +
            "WHERE id = ?)";
    private static final String SQL_SELECT_USER_BY_USERNAME = "SELECT u.*, r.name " +
            "FROM users u " +
            "INNER JOIN roles r ON u.role_id = r.id " +
            "WHERE u.username = ?";

    private UserDaoImpl() {

    }

    public static UserDAO getInstance() {
        if (userDaoImpl == null) {
            userDaoImpl = new UserDaoImpl();
        }
        return userDaoImpl;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                String username = resultSet.getString(2);
                String salt = resultSet.getString(3);
                String hash = resultSet.getString(4);
                UUID roleId = resultSet.getObject(5, UUID.class);
                String roleName = resultSet.getString(6);
                Role role = new Role(roleId, roleName);
                users.add(new User(id, username, salt, hash, role));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public User findById(UUID id) {
        User user = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)
        ) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString(2);
                String salt = resultSet.getString(3);
                String hash = resultSet.getString(4);
                UUID roleId = resultSet.getObject(5, UUID.class);
                String roleName = resultSet.getString(6);
                Role role = new Role(roleId, roleName);
                user = new User(id, username, salt, hash, role);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public User findByUsername(String username) {
        User user = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_USERNAME)
        ) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                String salt = resultSet.getString(3);
                String hash = resultSet.getString(4);
                UUID roleId = resultSet.getObject(5, UUID.class);
                String roleName = resultSet.getString(6);
                Role role = new Role(roleId, roleName);
                user = new User(id, username, salt, hash, role);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public boolean delete(UUID id) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID)
        ) {
            statement.setString(1, id.toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected == 1;
    }

    public boolean delete(User entity) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID)
        ) {
            statement.setString(1, entity.getId().toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected == 1;
    }

    public User create(User entity) {
        User user = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getSalt());
            statement.setString(3, entity.getHash());
            statement.setString(4, entity.getRole().getId().toString());
            ResultSet resultSet;
            UUID generatedId = null;
            int rowAffected = statement.executeUpdate();
            if(rowAffected == 1) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedId = (UUID) resultSet.getObject(1);
                }
                if (generatedId != null) {
                    user = entity;
                    user.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public User update(User entity) {
        User user = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)
        ) {
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getSalt());
            statement.setString(3, entity.getHash());
            statement.setString(4, entity.getRole().getId().toString());
            statement.setString(5, entity.getId().toString());
            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1) {
                user = entity;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}
