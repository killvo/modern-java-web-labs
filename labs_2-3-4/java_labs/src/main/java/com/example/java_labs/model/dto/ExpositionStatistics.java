package com.example.java_labs.model.dto;

import com.example.java_labs.domain.Exposition;
import com.example.java_labs.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExpositionStatistics extends Exposition {
    private Integer ticketsCount;

    public ExpositionStatistics(UUID id, boolean cancelled, String topic, Schedule schedule, Double price, Integer ticketsCount) {
        super(id, cancelled, topic, schedule, price);
        this.ticketsCount = ticketsCount;
    }
}
