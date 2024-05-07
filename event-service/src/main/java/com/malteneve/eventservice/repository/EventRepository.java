package com.malteneve.eventservice.repository;

import com.malteneve.eventservice.domain.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
}
