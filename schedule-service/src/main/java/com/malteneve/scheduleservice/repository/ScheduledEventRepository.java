package com.malteneve.scheduleservice.repository;

import com.malteneve.scheduleservice.domain.ScheduledEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledEventRepository extends CrudRepository<ScheduledEvent, Integer> {
}
