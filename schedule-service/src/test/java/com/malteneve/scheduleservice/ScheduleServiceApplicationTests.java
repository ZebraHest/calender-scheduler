package com.malteneve.scheduleservice;

import com.google.common.collect.Lists;
import com.malteneve.scheduleservice.controller.SchedulerController;
import com.malteneve.scheduleservice.domain.Event;
import com.malteneve.scheduleservice.domain.ScheduledEvent;
import com.malteneve.scheduleservice.service.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "eureka.client.enabled=false")
class ScheduleServiceApplicationTests {

    @MockBean
    EventService eventService;
    @LocalServerPort
    private int port;
    @Autowired
    private SchedulerController controller;

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        mockAllEvent();
        mockGetEvent();

        Event[] events = eventService.getEvents();

        controller.updateSchedule();
        ArrayList<ScheduledEvent> scheduledEventsFirst = Lists.newArrayList(controller.getScheduledEvents());
        controller.updateSingle(2);

        ArrayList<ScheduledEvent> scheduledEvents = Lists.newArrayList(controller.getScheduledEvents());
        assert scheduledEvents.size() == 2;
        assert scheduledEvents.getLast().getTitle().equals("3");
    }

    private void mockAllEvent() {

        Event mock1 = new Event();
        mock1.setTitle("1");
        mock1.setUserId("1");
        mock1.setId(1);
        mock1.setStartTime(LocalDateTime.of(2024, 5, 12, 12, 0));
        mock1.setEndTime(LocalDateTime.of(2024, 5, 12, 13, 0));

        Event mock2 = new Event();
        mock2.setTitle("2");
        mock2.setUserId("1");
        mock2.setId(2);
        mock2.setStartTime(LocalDateTime.of(2024, 5, 13, 12, 0));
        mock2.setEndTime(LocalDateTime.of(2024, 5, 13, 13, 0));

        Event[] eventArray = new Event[]{mock1, mock2};
        Mockito.when(eventService.getEvents()).thenReturn(eventArray);
    }

    private void mockGetEvent() {

        Event mock2 = new Event();
        mock2.setTitle("3");
        mock2.setUserId("1");
        mock2.setStartTime(LocalDateTime.of(2024, 5, 13, 14, 0));
        mock2.setEndTime(LocalDateTime.of(2024, 5, 13, 16, 0));

        Mockito.when(eventService.getEvent(2)).thenReturn(mock2);
    }

}
