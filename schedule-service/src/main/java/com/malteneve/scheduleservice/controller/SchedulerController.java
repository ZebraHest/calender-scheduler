package com.malteneve.scheduleservice.controller;

import com.malteneve.scheduleservice.domain.Event;
import com.malteneve.scheduleservice.domain.ScheduledEvent;
import com.malteneve.scheduleservice.logic.Scheduler;
import com.malteneve.scheduleservice.repository.ScheduledEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;

@RestController
public class SchedulerController {

    private static final String eventAllUrl = "http://localhost:8082/all";
    @Autowired
    ScheduledEventRepository repository;
    @Autowired
    Scheduler scheduler;

    @PostMapping("/update")
    public void updateSchedule() {
        RestTemplate restTemplate = new RestTemplate();
        Event[] events = restTemplate.getForObject(eventAllUrl, Event[].class);
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
