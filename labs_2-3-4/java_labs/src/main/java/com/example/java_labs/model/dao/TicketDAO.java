package com.example.java_labs.model.dao;

import com.example.java_labs.domain.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketDAO extends GenericDAO<UUID, Ticket> {
    List<Ticket> findByUserId(UUID userId);
}
