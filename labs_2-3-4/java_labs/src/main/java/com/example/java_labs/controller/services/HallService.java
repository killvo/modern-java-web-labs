package com.example.java_labs.controller.services;

import com.example.java_labs.controller.exception.HallWithThisFloorAndNumberAlreadyExistsException;
import com.example.java_labs.controller.exception.IncorrectCreateHalRequestException;
import com.example.java_labs.domain.Hall;
import com.example.java_labs.domain.User;
import com.example.java_labs.domain.constants.RoleEnum;
import com.example.java_labs.model.dao.*;
import com.example.java_labs.model.dao.factory.DaoFactory;
import com.example.java_labs.model.dao.factory.enums.DaoEnum;
import com.example.java_labs.model.dao.factory.impl.DaoFactoryImpl;
import com.example.java_labs.model.dao.impl.ExpositionDaoImpl;
import com.example.java_labs.model.dao.impl.ExpositionToHallDaoImpl;
import com.example.java_labs.model.dao.impl.ScheduleDaoImpl;
import com.example.java_labs.model.dao.impl.UserDaoImpl;

import java.util.List;
import java.util.UUID;

import static com.example.java_labs.controller.util.ValidationUtils.verifyUserHasRole;
import static com.example.java_labs.controller.util.ValidationUtils.verifyUserNotNull;

public class HallService {
    private static HallService expositionService;
    private ExpositionDAO expositionDAO;
    private ScheduleDAO scheduleDAO;
    private ExpositionToHallDAO expositionToHallDao;
    private UserDAO userDao;
    private HallDAO hallDAO;

    private HallService() {
        DaoFactory daoFactory = DaoFactoryImpl.getInstance();
        expositionDAO = (ExpositionDaoImpl) daoFactory.getDaoByName(DaoEnum.EXPOSITION_DAO);
        scheduleDAO = (ScheduleDaoImpl) daoFactory.getDaoByName(DaoEnum.SCHEDULE_DAO);
        expositionToHallDao = (ExpositionToHallDaoImpl) daoFactory.getDaoByName(DaoEnum.EXPOSITION_TO_HALL_DAO);
        userDao = (UserDaoImpl) daoFactory.getDaoByName(DaoEnum.USER_DAO);
        hallDAO = (HallDAO) daoFactory.getDaoByName(DaoEnum.HALL_DAO);
    }

    public static HallService getInstance() {
        if (expositionService == null) {
            expositionService = new HallService();
        }
        return expositionService;
    }

    public Hall createHall(UUID userId, String name, Integer floor, Integer hallNumber) {
        User user = userDao.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);

        if (name == null || floor == null || hallNumber == null) {
            throw new IncorrectCreateHalRequestException();
        }

        Hall hallInDb = hallDAO.findByFloorAndHallNumber(floor, hallNumber);
        if (hallInDb != null) {
            throw new HallWithThisFloorAndNumberAlreadyExistsException();
        }

        Hall hall = Hall.builder()
                .name(name)
                .floor(floor)
                .number(hallNumber)
                .build();

        return hallDAO.create(hall);
    }

    public List<Hall> getAllHalls(UUID userId) {
        User user = userDao.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);

        return hallDAO.findAll();
    }

}
