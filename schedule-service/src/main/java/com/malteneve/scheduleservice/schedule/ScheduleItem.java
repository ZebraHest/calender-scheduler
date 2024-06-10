package com.malteneve.scheduleservice.schedule;

import com.malteneve.scheduleservice.domain.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleItem implements Comparable<ScheduleItem> {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private List<Event> eventList = new ArrayList<>();

    public ScheduleItem(Event event) {
        this.startTime = event.getStartTime();
        this.endTime = event.getEndTime();
        eventList.add(event);
    }

    public ScheduleItem(Event event, LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        eventList.add(event);
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

    public List<Event> getEventList() {
        return eventList;
    }

    public void addEvent(Event event) {
        this.eventList.add(event);
    }

    public void addAllEvent(List<Event> events) {
        eventList.addAll(events);
    }

    @Override
    public int compareTo(ScheduleItem o) {
        return startTime.compareTo(o.startTime);
    }
}
