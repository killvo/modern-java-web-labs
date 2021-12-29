package com.example.java_labs.domain;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Schedule extends BaseEntity {
    private Date fromDate;
    private Date toDate;
    private String startTime;
    private String endTime;

    public Schedule(UUID id, Date fromDate, Date toDate, String startTime, String endTime) {
        super(id);
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
