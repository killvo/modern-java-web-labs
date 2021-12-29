package com.example.java_labs.model.dao;

import com.example.java_labs.domain.Exposition;
import com.example.java_labs.model.dto.ExpositionStatistics;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ExpositionDAO extends GenericDAO<UUID, Exposition> {
    List<ExpositionStatistics> selectStatistics();
    List<Exposition> findByTopic(String topic);
    List<Exposition> findByPrice(Double from, Double to);
    List<Exposition> findByDate(Date date);
}
