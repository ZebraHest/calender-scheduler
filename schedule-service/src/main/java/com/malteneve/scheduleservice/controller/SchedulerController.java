package com.malteneve.scheduleservice.controller;

import com.malteneve.scheduleservice.domain.Event;
import com.malteneve.scheduleservice.domain.ScheduledEvent;
import com.malteneve.scheduleservice.logic.Scheduler;
import com.malteneve.scheduleservice.repository.ScheduledEventRepository;
import com.malteneve.scheduleservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;

@RestController
public class SchedulerController {


    @Autowired
    ScheduledEventRepository repository;
    @Autowired
    Scheduler scheduler;
    @Autowired
    EventService eventService;

    @PostMapping("/add-all")
    public void updateSchedule() {
        Event[] events = eventService.getEvents();
        calculate(events);
    }

    @PostMapping("/add-single")
    public void addSingle(
            @RequestParam("eventId") Integer eventId
    ) {
        Event event = eventService.getEvent(eventId);
        calculate(new Event[]{event});
    }

    @PostMapping("/update")
    public void updateSingle(
            @RequestParam("eventId") Integer eventId
    ) {
        deleteSingle(eventId);
        addSingle(eventId);
    }

    @DeleteMapping("/delete")
    public void deleteSingle(
            @RequestParam("eventId") Integer eventId
    ) {
        scheduler.deleteEvent(eventId);
    }


    public void calculate(Event[] events) {
        if (events == null)
            return;

        Collection<ScheduledEvent> scheduledEvents = scheduler.schedule(Arrays.stream(events).toList());
        for (ScheduledEvent sE : scheduledEvents)
            repository.save(sE);
    }

    @GetMapping("/get")
    public @ResponseBody Iterable<ScheduledEvent> getScheduledEvents() {
        return repository.findAll();
    }

}
