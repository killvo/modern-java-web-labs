package com.example.java_labs.model.dao.factory;

import com.example.java_labs.domain.BaseEntity;
import com.example.java_labs.model.dao.GenericDAO;
import com.example.java_labs.model.dao.factory.enums.DaoEnum;

import java.util.UUID;

public interface DaoFactory {
    GenericDAO<UUID, ? extends BaseEntity> getDaoByName(DaoEnum daoEnum);
}
