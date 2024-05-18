package com.malteneve.eventservice;

import com.malteneve.eventservice.domain.Event;
import com.malteneve.eventservice.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

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
        event1.setTitle("Fødselsdag");
        event1.setDescription("Fars fødselsdag");
        event1.setStartTime(LocalDateTime.of(2024, 5, 15, 16, 0));
        event1.setEndTime(LocalDateTime.of(2024, 5, 15, 21, 0));
        event1.setUserId("1");

        Event event3 = new Event();
        event1.setTitle("Kanotur");
        event1.setDescription("Kanotur til Sverige");
        event1.setStartTime(LocalDateTime.of(2024, 5, 17, 8, 0));
        event1.setEndTime(LocalDateTime.of(2024, 5, 21, 22, 0));
        event1.setUserId("1");

        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);
    }
}
