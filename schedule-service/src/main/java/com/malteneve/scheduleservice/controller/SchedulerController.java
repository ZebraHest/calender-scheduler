package com.malteneve.scheduleservice.controller;

import com.malteneve.scheduleservice.domain.ScheduledEvent;
import com.malteneve.scheduleservice.repository.ScheduledEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchedulerController {


    @Autowired
    ScheduledEventRepository repository;

    @GetMapping("/get")
    public @ResponseBody Iterable<ScheduledEvent> getScheduledEvents() {
        return repository.findAll();
    }
}
