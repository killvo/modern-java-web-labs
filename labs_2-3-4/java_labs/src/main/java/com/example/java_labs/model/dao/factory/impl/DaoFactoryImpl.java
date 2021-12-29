package com.example.java_labs.model.dao.factory.impl;

import com.example.java_labs.domain.BaseEntity;
import com.example.java_labs.model.dao.GenericDAO;
import com.example.java_labs.model.dao.factory.DaoFactory;
import com.example.java_labs.model.dao.factory.enums.DaoEnum;
import com.example.java_labs.model.dao.impl.*;

import java.util.UUID;

public class DaoFactoryImpl implements DaoFactory {
    private static DaoFactoryImpl daoFactoryImpl;

    private DaoFactoryImpl() {
    }

    public static DaoFactoryImpl getInstance() {
        if (daoFactoryImpl == null) {
            daoFactoryImpl = new DaoFactoryImpl();
        }
        return daoFactoryImpl;
    }

    @Override
    public GenericDAO<UUID, ? extends BaseEntity> getDaoByName(DaoEnum daoEnum) {
        switch (daoEnum) {
            case USER_DAO: return UserDaoImpl.getInstance();
            case ROLE_DAO: return RoleDaoImpl.getInstance();
            case EXPOSITION_DAO: return ExpositionDaoImpl.getInstance();
            case EXPOSITION_TO_HALL_DAO: return ExpositionToHallDaoImpl.getInstance();
            case HALL_DAO: return HallDaoImpl.getInstance();
            case TICKET_DAO: return TicketDaoImpl.getInstance();
            case SCHEDULE_DAO: return ScheduleDaoImpl.getInstance();
            default:
                throw new EnumConstantNotPresentException(DaoEnum.class, daoEnum.name());
        }
    }
}
