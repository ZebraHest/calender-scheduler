package com.malteneve.scheduleservice.logic;

import com.malteneve.scheduleservice.domain.Event;
import com.malteneve.scheduleservice.domain.ScheduledEvent;
import com.malteneve.scheduleservice.kafka.producer.MessageProducer;
import com.malteneve.scheduleservice.repository.ScheduledEventRepository;
import com.malteneve.scheduleservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
public class RequestHandler {

    @Autowired
    ScheduledEventRepository repository;

    @Autowired
    Scheduler scheduler;

    @Autowired
    EventService eventService;

    @Autowired
    private MessageProducer messageProducer;

    public void addAllEvents() {
        Event[] events = eventService.getEvents();
        calculate(events);
    }

    public void addEvent(Integer eventId) {
        Event event = eventService.getEvent(eventId);
        calculate(new Event[]{event});
        sendKafkaMessageUpdate(event.getUserId());
    }

    public void updateEvent(Integer eventId) {
        deleteEvent(eventId);
        addEvent(eventId);
    }

    public void deleteEvent(Integer eventId) {
        scheduler.deleteEvent(eventId);
        sendKafkaMessageUpdate("event.getUserId()"); //TODO access userId
    }

    public void calculate(Event[] events) {
        if (events == null)
            return;

        Collection<ScheduledEvent> scheduledEvents = scheduler.schedule(Arrays.stream(events).toList());
        for (ScheduledEvent sE : scheduledEvents)
            repository.save(sE);
    }


    private void sendKafkaMessageUpdate(String userId) {
        messageProducer.sendMessage("update-schedule", userId);
    }
}
