package com.malteneve.eventservice.controller;

import com.malteneve.eventservice.domain.Event;
import com.malteneve.eventservice.repository.EventRepository;
import com.malteneve.eventservice.validator.AddEventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AddEventValidator eventValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(eventValidator);
    }

    @PostMapping("/add")
    public String createEvent(
            @Validated @RequestBody Event event
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
