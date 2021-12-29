package com.example.java_labs.controller.services;

import com.example.java_labs.controller.command.constants.ShowBy;
import com.example.java_labs.controller.exception.ExpositionAlreadyCancelledException;
import com.example.java_labs.controller.exception.ExpositionNotFoundException;
import com.example.java_labs.controller.exception.IncorrectCreateExpositionRequestException;
import com.example.java_labs.controller.exception.InvalidIdException;
import com.example.java_labs.domain.*;
import com.example.java_labs.domain.constants.RoleEnum;
import com.example.java_labs.model.dao.*;
import com.example.java_labs.model.dao.factory.DaoFactory;
import com.example.java_labs.model.dao.factory.enums.DaoEnum;
import com.example.java_labs.model.dao.factory.impl.DaoFactoryImpl;
import com.example.java_labs.model.dao.impl.ExpositionDaoImpl;
import com.example.java_labs.model.dao.impl.ExpositionToHallDaoImpl;
import com.example.java_labs.model.dao.impl.ScheduleDaoImpl;
import com.example.java_labs.model.dao.impl.UserDaoImpl;
import com.example.java_labs.model.dto.ExpositionDetails;
import com.example.java_labs.model.dto.ExpositionStatistics;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.java_labs.controller.util.ValidationUtils.verifyUserHasRole;
import static com.example.java_labs.controller.util.ValidationUtils.verifyUserNotNull;

public class ExpositionService {
    private static ExpositionService expositionService;
    private ExpositionDAO expositionDAO;
    private ScheduleDAO scheduleDAO;
    private ExpositionToHallDAO expositionToHallDao;
    private UserDAO userDao;

    private ExpositionService() {
        DaoFactory daoFactory = DaoFactoryImpl.getInstance();
        expositionDAO = (ExpositionDaoImpl) daoFactory.getDaoByName(DaoEnum.EXPOSITION_DAO);
        scheduleDAO = (ScheduleDaoImpl) daoFactory.getDaoByName(DaoEnum.SCHEDULE_DAO);
        expositionToHallDao = (ExpositionToHallDaoImpl) daoFactory.getDaoByName(DaoEnum.EXPOSITION_TO_HALL_DAO);
        userDao = (UserDaoImpl) daoFactory.getDaoByName(DaoEnum.USER_DAO);
    }

    public static ExpositionService getInstance() {
        if (expositionService == null) {
            expositionService = new ExpositionService();
        }
        return expositionService;
    }

    public List<Exposition> getExpositions(ShowBy showBy, String topic, Double priceFrom, Double priceTo, Date date) {
        if (showBy == null) {
            return expositionDAO.findAll();
        }

        switch (showBy) {
            case TOPIC:
                return expositionDAO.findByTopic(topic);
            case DATE:
                return expositionDAO.findByDate(date);
            case PRICE:
                return expositionDAO.findByPrice(priceFrom, priceTo);
            default:
                return expositionDAO.findAll();
        }
    }

    public ExpositionDetails getExpositionDetails(UUID userId, UUID expositionId) {
        User user = userDao.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.USER);

        Exposition exposition = expositionDAO.findById(expositionId);
        if (exposition == null) {
            throw new ExpositionNotFoundException();
        }

        List<ExpositionToHall> expositionToHalls = expositionToHallDao.findByExpositionId(expositionId);
        List<Hall> halls = expositionToHalls.stream()
                .map(ExpositionToHall::getHall)
                .collect(Collectors.toList());

        ExpositionDetails expositionDetails = new ExpositionDetails();
        expositionDetails.setExposition(exposition);
        expositionDetails.setHalls(halls);

        return expositionDetails;
    }

    public List<Exposition> getExpositionsForAdmin(UUID userId) {
        User user = userDao.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);

        return expositionDAO.findAll();
    }

    public List<ExpositionStatistics> getStatisticsForAdmin(UUID userId) {
        User user = userDao.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);

        return expositionDAO.selectStatistics();
    }

    public Exposition createExposition(UUID userId, String topic, Double price, Date fromDate, Date toDate,
                                       String startTime, String endTime, String[] selectedHalls) {
        User user = userDao.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);
        verifyOnNull(topic, price, fromDate, toDate, startTime, endTime, selectedHalls);

        List<UUID> hallsIds = Arrays.stream(selectedHalls)
                .map(UUID::fromString)
                .collect(Collectors.toList());

        Schedule schedule = Schedule.builder()
                .fromDate(fromDate)
                .toDate(toDate)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        Schedule createdSchedule = scheduleDAO.create(schedule);

        Exposition exposition = Exposition.builder()
                .price(price)
                .topic(topic)
                .schedule(createdSchedule)
                .build();

        Exposition createdExposition = expositionDAO.create(exposition);

        hallsIds.forEach(hallId -> {
            ExpositionToHall expositionToHall = ExpositionToHall.builder()
                    .exposition(createdExposition)
                    .hall(new Hall(hallId))
                    .build();
            expositionToHallDao.create(expositionToHall);
        });

        return createdExposition;
    }

    private void verifyOnNull(String topic, Double price, Date fromDate, Date toDate,
                              String startTime, String endTime, String[] selectedHalls) {
        if (topic == null || price == null || fromDate == null || toDate == null ||
                startTime == null || endTime == null || selectedHalls == null) {
            throw new IncorrectCreateExpositionRequestException();
        }
    }

    public void cancelExposition(UUID userId, UUID expositionId) {
        User user = userDao.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);
        if (expositionId == null) {
            throw new InvalidIdException();
        }

        Exposition exposition = expositionDAO.findById(expositionId);
        if (exposition.isCancelled()) {
            throw new ExpositionAlreadyCancelledException();
        }
        exposition.setCancelled(true);
        expositionDAO.update(exposition);
    }


}
