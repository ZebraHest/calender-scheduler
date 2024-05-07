package com.malteneve.eventservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.time.Period;

@Entity
public class Event {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Period period;
    private String userId;

    public Event() {
    }

    public Event(String title, String description, LocalDateTime startTime, LocalDateTime endTime, Period period, String userId) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.period = period;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Period getPeriod() {
        return period;
    }

    public String getUserId() {
        return userId;
    }
}
