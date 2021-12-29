package com.example.java_labs.domain;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Exposition extends BaseEntity {
    private boolean cancelled;
    private String topic;
    private Schedule schedule;
    private Double price;

    public Exposition(UUID id, boolean cancelled, String topic, Schedule schedule, Double price) {
        super(id);
        this.cancelled = cancelled;
        this.topic = topic;
        this.schedule = schedule;
        this.price = price;
    }
}
