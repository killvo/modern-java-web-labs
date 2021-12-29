package com.example.java_labs.model.dao;

import com.example.java_labs.domain.User;

import java.util.UUID;

public interface UserDAO extends GenericDAO<UUID, User> {
    User findByUsername(String username);
}
