package com.example.java_labs.model.dao.impl;

import com.example.java_labs.domain.Role;
import com.example.java_labs.model.dao.RoleDAO;
import com.example.java_labs.model.dao.connection.impl.ConnectionFactoryImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class RoleDaoImpl implements RoleDAO {
    private static RoleDAO roleDaoImpl;
    private static final String SQL_SELECT_ROLE_BY_ID = "SELECT * FROM roles WHERE id = ?";
    private static final String SQL_SELECT_ROLE_BY_NAME = "SELECT * FROM roles WHERE name = ?";

    private RoleDaoImpl() {

    }

    public static RoleDAO getInstance() {
        if (roleDaoImpl == null) {
            roleDaoImpl = new RoleDaoImpl();
        }
        return roleDaoImpl;
    }

    @Override
    public List<Role> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Role findById(UUID id) {
        Role role = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROLE_BY_ID)
        ) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                role = new Role(id, name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return role;
    }

    @Override
    public boolean delete(UUID id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Role entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Role create(Role entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Role update(Role entity) {
        throw new UnsupportedOperationException();
    }

    public Role findByName(String name) {
        Role role = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROLE_BY_NAME)
        ) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                role = new Role(id, name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return role;
    }
}
