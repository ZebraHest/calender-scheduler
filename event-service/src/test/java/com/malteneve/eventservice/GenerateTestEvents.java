package com.malteneve.eventservice;

import com.malteneve.eventservice.domain.Event;
import com.malteneve.eventservice.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(properties = "eureka.client.enabled=false")
public class GenerateTestEvents {

    @Autowired
    private EventRepository eventRepository;


    @Test
    public void generateSomeEvents() {

        if (eventRepository.count() != 0) {
            return;
        }

        Event event1 = new Event();
        event1.setTitle("Læge");
        event1.setDescription("Få testet allergier");
        event1.setStartTime(LocalDateTime.of(2024, 5, 12, 10, 0));
        event1.setEndTime(LocalDateTime.of(2024, 5, 12, 11, 0));
        event1.setUserId("1");


        Event event2 = new Event();
        event2.setTitle("Fødselsdag");
        event2.setDescription("Fars fødselsdag");
        event2.setStartTime(LocalDateTime.of(2024, 5, 15, 16, 0));
        event2.setEndTime(LocalDateTime.of(2024, 5, 15, 21, 0));
        event2.setUserId("1");

        Event event3 = new Event();
        event3.setTitle("Kanotur");
        event3.setDescription("Kanotur til Sverige");
        event3.setStartTime(LocalDateTime.of(2024, 5, 17, 8, 0));
        event3.setEndTime(LocalDateTime.of(2024, 5, 21, 22, 2));
        event3.setUserId("1");
        event3.setDuration(Duration.ofMinutes(1));
        event3.setIsFlexible(false);
        event3.setIsRepeating(true);
        event3.setRepeatDays(List.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));
        event3.setRepeatStartDate(LocalDate.of(2024, 5, 1));
        event3.setRepeatEndDate(LocalDate.of(2024, 5, 30));


        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);
    }
}
