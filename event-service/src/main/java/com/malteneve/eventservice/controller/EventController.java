package com.malteneve.eventservice.controller;

import com.malteneve.eventservice.domain.Event;
import com.malteneve.eventservice.kafka.producer.MessageProducer;
import com.malteneve.eventservice.repository.EventRepository;
import com.malteneve.eventservice.validator.AddEventValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MessageProducer messageProducer;


    @Autowired
    private AddEventValidator eventValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(eventValidator);
    }

    @PostMapping("/add")
    @ResponseBody
    public HttpStatus createEvent(
            @Validated @Valid @RequestBody Event event
    ) {
        eventRepository.save(event);
        messageProducer.sendMessage("new-event", event.getId().toString());
        return HttpStatus.CREATED;
    }

    @PutMapping("/update")
    @ResponseBody
    public HttpStatus updateEvent(
            @Validated @Valid @RequestBody Event event
    ) {
//        eventRepository.deleteById(event.getId());
        eventRepository.save(event);
        messageProducer.sendMessage("update-event", event.getId().toString());
        return HttpStatus.OK;
    }

    @GetMapping("/all")
    public Iterable<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/get")
    public Event getEvent(
            @RequestParam("eventId") Integer eventId
    ) {
        return eventRepository.findById(eventId).orElse(null);
    }


}
