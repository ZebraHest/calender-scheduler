package com.malteneve.scheduleservice.service;

import com.malteneve.scheduleservice.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EventService {

    private static final String eventAllUrl = "http://localhost:8082/all";
    private static final String eventSingleUrl = "http://localhost:8082/get?eventId=";
    private final RestTemplate restTemplate;

    @Autowired
    public EventService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Event[] getEvents() {
        return restTemplate.getForObject(eventAllUrl, Event[].class);
    }


    public Event getEvent(Integer eventId) {
        return restTemplate.getForObject(eventSingleUrl + eventId, Event.class);
    }


}
