package com.example.java_labs.model.dao;

import com.example.java_labs.domain.ExpositionToHall;

import java.util.List;
import java.util.UUID;

public interface ExpositionToHallDAO extends GenericDAO<UUID, ExpositionToHall> {
    List<ExpositionToHall> findByExpositionId(UUID expositionId);
}
