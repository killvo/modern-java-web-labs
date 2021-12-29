package com.example.java_labs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Ticket extends BaseEntity {
    private LocalDateTime datetime;
    private User user;
    private Exposition exposition;

    public Ticket(UUID id, LocalDateTime datetime, User user, Exposition exposition) {
        super(id);
        this.datetime = datetime;
        this.user = user;
        this.exposition = exposition;
    }
}
