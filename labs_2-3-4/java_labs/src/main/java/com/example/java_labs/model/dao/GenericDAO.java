package com.example.java_labs.model.dao;

import com.example.java_labs.domain.BaseEntity;

import java.util.List;

public interface GenericDAO<K, T extends BaseEntity> {
    List<T> findAll();
    T findById(K id);
    boolean delete(K id);
    boolean delete(T entity);
    T create(T entity);
    T update(T entity);
}
