package com.malteneve.scheduleservice.controller;

import com.malteneve.scheduleservice.domain.Event;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SchedulerController {

    @GetMapping("/")
    public @ResponseBody String createEvent() {

        RestTemplate restTemplate = new RestTemplate();

        Event[] objects = restTemplate.getForObject("http://localhost:8082/all", Event[].class);
        return "Event created.";
    }

}
