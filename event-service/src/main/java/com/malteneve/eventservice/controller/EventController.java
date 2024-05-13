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
            @RequestParam(value = "name") String name) {
        System.out.println("Starting to create event");
        Event n = new Event();
        n.setTitle(name);
        eventRepository.save(n);
        return "Event created.";
    }

    @GetMapping("/all")
    public Iterable<Event> getAllUsers() {
        return eventRepository.findAll();
    }
}
