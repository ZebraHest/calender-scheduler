package com.malteneve.scheduleservice.itest;

import com.malteneve.scheduleservice.domain.Event;
import com.malteneve.scheduleservice.domain.ScheduledEvent;
import com.malteneve.scheduleservice.logic.Scheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootTest
public class SchedulerTest {

    @Autowired
    Scheduler scheduler;

    @Test
    public void testRepeating() {
        ArrayList<Event> events = new ArrayList<>();

        Event event1 = new Event();
        event1.setTitle("1");
        event1.setStartTime(LocalDateTime.of(2024, 5, 12, 10, 0));
        event1.setEndTime(LocalDateTime.of(2024, 5, 12, 11, 0));
        events.add(event1);

        Event event3 = new Event();
        event3.setTitle("3");
        event3.setStartTime(LocalDateTime.of(2024, 5, 12, 12, 0));
        event3.setEndTime(LocalDateTime.of(2024, 5, 12, 13, 0));
        event3.setRepeating(true);
        event3.setRepeatDays(new ArrayList<>(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY)));
        event3.setRepeatStartDate(LocalDate.of(2024, 5, 1));
        event3.setRepeatEndDate(LocalDate.of(2024, 5, 31));
        events.add(event3);


        Collection<ScheduledEvent> scheduledEvents = scheduler.schedule(events);


        assert scheduledEvents.size() == 10;
    }

    @Test
    public void testFlexible() {
        ArrayList<Event> events = new ArrayList<>();

        Event event1 = new Event();
        event1.setTitle("1");
        event1.setStartTime(LocalDateTime.of(2024, 5, 12, 10, 0));
        event1.setEndTime(LocalDateTime.of(2024, 5, 12, 11, 0));
        events.add(event1);

        Event event2 = new Event();
        event2.setTitle("2");
        event2.setStartTime(LocalDateTime.of(2024, 5, 12, 10, 0));
        event2.setEndTime(LocalDateTime.of(2024, 5, 18, 0, 0));
        event2.setDuration(Duration.ofHours(2));
        event2.setFlexible(true);
        events.add(event2);

        Event event3 = new Event();
        event3.setTitle("3");
        event3.setStartTime(LocalDateTime.of(2024, 5, 12, 12, 0));
        event3.setEndTime(LocalDateTime.of(2024, 5, 12, 16, 0));
        events.add(event3);

        Collection<ScheduledEvent> scheduledEvents = scheduler.schedule(events);

        assert scheduledEvents == null;
    }
}
