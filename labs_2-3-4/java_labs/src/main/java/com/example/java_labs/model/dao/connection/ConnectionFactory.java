package com.example.java_labs.model.dao.connection;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection getConnection();
}
