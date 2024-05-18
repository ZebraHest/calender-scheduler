package com.malteneve.scheduleservice.itest;

import com.malteneve.scheduleservice.domain.Event;
import com.malteneve.scheduleservice.schedule.Schedule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

//@SpringBootTest(properties = "eureka.client.enabled=false")
public class ScheduleTest {

    @Test
    public void testSchedule() {

        Event event1 = new Event();
        event1.setStartTime(LocalDateTime.of(2024, 5, 12, 10, 0));
        event1.setEndTime(LocalDateTime.of(2024, 5, 12, 11, 0));

        Event event2 = new Event();
        event2.setStartTime(LocalDateTime.of(2024, 5, 12, 14, 0));
        event2.setEndTime(LocalDateTime.of(2024, 5, 12, 15, 0));

        Event event3 = new Event();
        event3.setStartTime(LocalDateTime.of(2024, 5, 12, 11, 0));
        event3.setEndTime(LocalDateTime.of(2024, 5, 12, 13, 0));

        Event event4 = new Event();
        event4.setStartTime(LocalDateTime.of(2024, 5, 12, 12, 30));
        event4.setEndTime(LocalDateTime.of(2024, 5, 12, 14, 30));


        Schedule schedule = new Schedule();

        assert schedule.addIfAble(event1);
        assert schedule.addIfAble(event2);
        assert schedule.addIfAble(event3);
        assert !schedule.addIfAble(event4);
        schedule.forceAdd(event4);


        assert schedule != null;
    }
}
