package com.malteneve.scheduleservice.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

//@Entity
public class Event {

    //    @Id
//    @GeneratedValue
    private Integer id;

    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDate repeatStartDate;
    private LocalDate repeatEndDate;
    private Period period;
    private String userId;
    private Boolean isStartTimePrecise;
    private Boolean isEndTimePrecise;
    private List<DayOfWeek> repeatDays;

    public Event() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getRepeatStartDate() {
        return repeatStartDate;
    }

    public void setRepeatStartDate(LocalDate repeatStartDate) {
        this.repeatStartDate = repeatStartDate;
    }

    public LocalDate getRepeatEndDate() {
        return repeatEndDate;
    }

    public void setRepeatEndDate(LocalDate repeatEndDate) {
        this.repeatEndDate = repeatEndDate;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getStartTimePrecise() {
        return isStartTimePrecise;
    }

    public void setStartTimePrecise(Boolean startTimePrecise) {
        isStartTimePrecise = startTimePrecise;
    }

    public Boolean getEndTimePrecise() {
        return isEndTimePrecise;
    }

    public void setEndTimePrecise(Boolean endTimePrecise) {
        isEndTimePrecise = endTimePrecise;
    }

    public List<DayOfWeek> getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(List<DayOfWeek> repeatDays) {
        this.repeatDays = repeatDays;
    }
}
