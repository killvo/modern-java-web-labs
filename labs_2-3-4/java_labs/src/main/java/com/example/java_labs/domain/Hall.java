package com.example.java_labs.domain;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Hall extends BaseEntity {
    private String name;
    private Integer floor;
    private Integer number;

    public Hall(UUID id, String name, Integer floor, Integer number) {
        super(id);
        this.name = name;
        this.floor = floor;
        this.number = number;
    }

    public Hall(UUID id) {
        super(id);
    }
}
