package com.malteneve.scheduleservice.logic;

import com.malteneve.scheduleservice.domain.Event;
import com.malteneve.scheduleservice.domain.ScheduledEvent;
import com.malteneve.scheduleservice.schedule.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

public class Scheduler {


    public Collection<ScheduledEvent> schedule(List<Event> events) {

        HashMap<MapKey, ScheduledEvent> lockedEvents = new HashMap<>();
        Schedule schedule = new Schedule();

        events = expandRepeatingEvents(events);

        List<Event> flexibleEvents = events.stream()
                .filter(Event::getFlexible)
                .toList();

        List<Event> inflexibleEvents = events.stream()
                .filter(Predicate.not(Event::getFlexible))
                .toList();

        for (Event event : inflexibleEvents) {
            MapKey key = new MapKey(event.getStartTime(), event.getEndTime());
            ScheduledEvent scheduledEvent = createScheduledEvent(event);
            lockedEvents.put(key, scheduledEvent);
            schedule.forceAdd(event);
        }

        for (Event event : flexibleEvents) {
            if (schedule.addIfAble(event)) {
                continue;
            }
            LocalDateTime firstAvailableTime = schedule.getFirstAvailableTime(event);
            if (firstAvailableTime == null) {
                return Collections.emptyList();
            }
            LocalDateTime newEndTime = firstAvailableTime.plus(event.getDuration());
            MapKey key = new MapKey(firstAvailableTime, newEndTime);
            ScheduledEvent scheduledEvent = createScheduledEvent(event, firstAvailableTime, newEndTime);
            lockedEvents.put(key, scheduledEvent);
            schedule.add(event, firstAvailableTime, newEndTime);
        }


        return lockedEvents.values();
    }

    private ScheduledEvent createScheduledEvent(Event event) {
        return createScheduledEvent(event, event.getStartTime(), event.getEndTime());
    }

    private ScheduledEvent createScheduledEvent(Event event, LocalDateTime startTime, LocalDateTime endTime) {
        ScheduledEvent scheduledEvent = new ScheduledEvent();
        scheduledEvent.setTitle(event.getTitle());
        scheduledEvent.setUserId(event.getUserId());
        scheduledEvent.setStartTime(startTime);
        scheduledEvent.setEndTime(endTime);
        return scheduledEvent;
    }


    private List<Event> expandRepeatingEvents(List<Event> events) {
        ArrayList<Event> expandedList = new ArrayList<>();

        for (Event event : events) {
            if (!event.getRepeating()) {
                expandedList.add(event);
                continue;
            }

            LocalDate currentDate = event.getRepeatStartDate();

            while (!currentDate.isAfter(event.getRepeatEndDate())) {
                if (event.getRepeatDays().contains(currentDate.getDayOfWeek())) {
                    Event newEvent = new Event();
                    newEvent.setTitle(event.getTitle());
                    newEvent.setStartTime(LocalDateTime.of(currentDate, event.getStartTime().toLocalTime()));
                    newEvent.setEndTime(LocalDateTime.of(currentDate, event.getEndTime().toLocalTime()));
                    newEvent.setFlexible(event.getFlexible());
                    newEvent.setDuration(event.getDuration());
                    newEvent.setUserId(event.getUserId());
                    expandedList.add(newEvent);
                }
                currentDate = currentDate.plusDays(1);
            }
        }
        return expandedList;
    }

    private boolean isConflict(Event lockedEvent, Event newEvent) {
        //Start at the same time
        if (newEvent.getStartTime().isEqual(lockedEvent.getStartTime()))
            return true;

        //Starts before and end after locked start
        if (newEvent.getStartTime().isBefore(lockedEvent.getStartTime())
                && newEvent.getEndTime().isAfter(lockedEvent.getStartTime()))
            return true;

        //Starts after locked start and before locked end
        if (newEvent.getStartTime().isAfter(lockedEvent.getStartTime())
                && newEvent.getStartTime().isBefore(lockedEvent.getEndTime()))
            return true;

        return false;
    }
}


record MapKey(LocalDateTime startTime, LocalDateTime endTime) {
}
