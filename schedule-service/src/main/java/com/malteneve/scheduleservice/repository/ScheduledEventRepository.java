package com.malteneve.scheduleservice.repository;

import com.malteneve.scheduleservice.domain.ScheduledEvent;
import org.springframework.data.repository.CrudRepository;

public interface ScheduledEventRepository extends CrudRepository<ScheduledEvent, Integer> {
}
