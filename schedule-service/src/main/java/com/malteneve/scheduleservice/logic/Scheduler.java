package com.malteneve.scheduleservice.logic;

import com.google.common.collect.Lists;
import com.malteneve.scheduleservice.domain.Event;
import com.malteneve.scheduleservice.domain.ScheduledEvent;
import com.malteneve.scheduleservice.repository.ScheduledEventRepository;
import com.malteneve.scheduleservice.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Service
public class Scheduler {

    @Autowired
    ScheduledEventRepository repository;

    private static Event getEvent(Event event, LocalDate currentDate) {
        Event newEvent = new Event();
        newEvent.setTitle(event.getTitle());
        newEvent.setStartTime(LocalDateTime.of(currentDate, event.getStartTime().toLocalTime()));
        newEvent.setEndTime(LocalDateTime.of(currentDate, event.getEndTime().toLocalTime()));
        newEvent.setFlexible(event.getFlexible());
        newEvent.setDuration(event.getDuration());
        newEvent.setUserId(event.getUserId());
        newEvent.setId(event.getId());
        return newEvent;
    }

    public Collection<ScheduledEvent> schedule(List<Event> events) {

        List<ScheduledEvent> lockedEvents = findScheduledEvents();
        Schedule schedule = new Schedule();

        events = removeScheduledEvents(events, lockedEvents);

        events = expandRepeatingEvents(events);

        List<Event> flexibleEvents = events.stream()
                .filter(Event::getFlexible)
                .toList();

        List<Event> inflexibleEvents = events.stream()
                .filter(Predicate.not(Event::getFlexible))
                .toList();

        for (Event event : inflexibleEvents) {
            ScheduledEvent scheduledEvent = createScheduledEvent(event);
            lockedEvents.add(scheduledEvent);
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
            ScheduledEvent scheduledEvent = createScheduledEvent(event, firstAvailableTime, newEndTime);
            lockedEvents.add(scheduledEvent);
            schedule.add(event, firstAvailableTime, newEndTime);
        }


        return lockedEvents;
    }

    private List<Event> removeScheduledEvents(List<Event> events, List<ScheduledEvent> lockedEvents) {
        List<Integer> lockedIds = lockedEvents
                .stream()
                .map(ScheduledEvent::getEventId)
                .toList();

        return events.stream().filter(e -> !lockedIds.contains(e.getId())).toList();
    }

    private List<ScheduledEvent> findScheduledEvents() {
        return Lists.newArrayList(repository.findAll());
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
        scheduledEvent.setEventId(event.getId());
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
                    Event newEvent = getEvent(event, currentDate);
                    expandedList.add(newEvent);
                }
                currentDate = currentDate.plusDays(1);
            }
        }
        return expandedList;
    }

    public void deleteEvent(Integer eventId) {
        List<ScheduledEvent> list = findScheduledEvents()
                .stream()
                .filter(e -> e.getEventId().equals(eventId))
                .toList();
        repository.deleteAll(list);
    }
}
