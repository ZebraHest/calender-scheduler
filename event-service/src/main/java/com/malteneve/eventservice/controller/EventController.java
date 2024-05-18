package com.malteneve.eventservice.controller;

import com.malteneve.eventservice.domain.Event;
import com.malteneve.eventservice.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @PostMapping("/add")
    public @ResponseBody String createEvent(
            @RequestBody Event event
    ) {
        System.out.println("Starting to create event");

        eventRepository.save(event);
        return "Event created.";
    }

    @GetMapping("/all")
    public Iterable<Event> getAllUsers() {
        return eventRepository.findAll();
    }
}
