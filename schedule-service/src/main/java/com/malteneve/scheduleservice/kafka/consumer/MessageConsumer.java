package com.malteneve.scheduleservice.kafka.consumer;

import com.malteneve.scheduleservice.controller.SchedulerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @Autowired
    SchedulerController controller;

    @KafkaListener(topics = "new-event", groupId = "schedule-service")
    public void listenNewEvent(String message) {
        System.out.println("Received new event message in scheduler: " + message);
        controller.addSingle(Integer.valueOf(message));
    }

    @KafkaListener(topics = "update-event", groupId = "schedule-service")
    public void listenUpdateEvent(String message) {
        System.out.println("Received update event message in scheduler: " + message);
        controller.updateSingle(Integer.valueOf(message));
    }

}