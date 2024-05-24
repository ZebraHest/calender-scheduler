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
    public void listen(String message) {
        System.out.println("Received message in scheduler: " + message);
        controller.updateSingle(Integer.valueOf(message));
    }

}