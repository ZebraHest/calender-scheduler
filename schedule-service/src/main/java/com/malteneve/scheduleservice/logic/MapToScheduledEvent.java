package com.malteneve.scheduleservice.logic;

import com.malteneve.scheduleservice.domain.Event;
import com.malteneve.scheduleservice.domain.ScheduledEvent;

public class MapToScheduledEvent {

    static public ScheduledEvent map(Event event) {
        ScheduledEvent scheduledEvent = new ScheduledEvent();
        scheduledEvent.setTitle(event.getTitle());
        scheduledEvent.setStartTime(event.getStartTime());
        scheduledEvent.setEndTime(event.getEndTime());
        scheduledEvent.setUserId(event.getUserId());
        return scheduledEvent;
    }
}
