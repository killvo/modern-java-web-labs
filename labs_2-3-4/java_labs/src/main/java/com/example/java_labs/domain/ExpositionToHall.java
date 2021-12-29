package com.example.java_labs.domain;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class ExpositionToHall extends BaseEntity {
    private Exposition exposition;
    private Hall hall;

    public ExpositionToHall(UUID id, Exposition exposition, Hall hall) {
        super(id);
        this.exposition = exposition;
        this.hall = hall;
    }
}
