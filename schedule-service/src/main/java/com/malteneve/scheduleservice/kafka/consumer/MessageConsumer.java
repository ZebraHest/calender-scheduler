package com.malteneve.scheduleservice.kafka.consumer;

import com.malteneve.scheduleservice.logic.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @Autowired
    RequestHandler requestHandler;

    @KafkaListener(topics = "new-event", groupId = "schedule-service")
    public void listenNewEvent(String message) {
        System.out.println("Received new event message in scheduler: " + message);
        requestHandler.addEvent(Integer.valueOf(message));
    }

    @KafkaListener(topics = "update-event", groupId = "schedule-service")
    public void listenUpdateEvent(String message) {
        System.out.println("Received update event message in scheduler: " + message);
        requestHandler.updateEvent(Integer.valueOf(message));
    }

    @KafkaListener(topics = "delete-event", groupId = "schedule-service")
    public void listenDeleteEvent(String message) {
        System.out.println("Received delete event message in scheduler: " + message);
        requestHandler.deleteEvent(Integer.valueOf(message));
    }

}