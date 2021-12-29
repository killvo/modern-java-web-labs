package com.example.java_labs.model.dao;

import com.example.java_labs.domain.Role;

import java.util.UUID;

public interface RoleDAO extends GenericDAO<UUID, Role> {
    Role findByName(String name);
}
