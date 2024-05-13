package com.malteneve.scheduleservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ScheduledEvent {
    @Id
    @GeneratedValue
    private Integer id;

    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDate repeatStartDate;
    private LocalDate repeatEndDate;
    private String userId;
    private List<DayOfWeek> repeatDays;

}
