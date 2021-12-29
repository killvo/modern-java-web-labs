package com.example.java_labs.model.dao;

import com.example.java_labs.domain.Hall;

import java.util.UUID;

public interface HallDAO extends GenericDAO<UUID, Hall> {
    Hall findByFloorAndHallNumber(Integer floor, Integer hallNumber);

}
