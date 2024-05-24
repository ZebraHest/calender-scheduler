package com.malteneve.eventservice.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "new-event", groupId = "event-service")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }

}