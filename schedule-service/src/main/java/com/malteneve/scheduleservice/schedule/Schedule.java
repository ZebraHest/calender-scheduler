package com.malteneve.scheduleservice.schedule;

import com.malteneve.scheduleservice.domain.Event;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Schedule {


    private TreeMap<LocalDateTime, ScheduleItem> schedule = new TreeMap<>();


    public boolean addIfAble(Event event) {
        if (isAbleToAdd(event)) {
            add(event);
            return true;
        }
        return false;
    }

    public void forceAdd(Event event) {
        if (isAbleToAdd(event)) {
            add(event);
        } else {

            Map.Entry<LocalDateTime, ScheduleItem> lowerEntry = schedule.floorEntry(event.getStartTime());
            NavigableMap<LocalDateTime, ScheduleItem> subMap = schedule.subMap(event.getStartTime(), true, event.getEndTime(), false);

            ScheduleItem lower = lowerEntry != null ? lowerEntry.getValue() : null;
            ScheduleItem item = new ScheduleItem(event);


            if (lower != null && lower.getEndTime().isAfter(event.getStartTime())) {
                schedule.remove(lower.getStartTime());
                item.addAllEvent(lower.getEventList());
                item.setStartTime(lower.getStartTime());
            }
            if (subMap != null) {
                for (ScheduleItem oldItem : subMap.values()) {
                    schedule.remove(oldItem.getStartTime());
                    item.addAllEvent(oldItem.getEventList());
                    if (oldItem.getEndTime().isAfter(item.getEndTime()))
                        item.setEndTime(oldItem.getEndTime());
                }

            }
            schedule.put(item.getStartTime(), item);
        }
    }

    public LocalDateTime getFirstAvailableTime(Event event) {
        return getFirstAvailableTime(event.getStartTime(), event.getEndTime(), event.getDuration());
    }

    public LocalDateTime getFirstAvailableTime(LocalDateTime startTime, LocalDateTime endTime, Duration duration) {

        Map.Entry<LocalDateTime, ScheduleItem> lowerEntry = schedule.floorEntry(startTime);
        NavigableMap<LocalDateTime, ScheduleItem> subMap = schedule.subMap(startTime, true, endTime, false);

        LocalDateTime possibleStartTime;

        if (lowerEntry != null) {
            possibleStartTime = lowerEntry.getValue().getEndTime();
            if (isAbleToAdd(possibleStartTime, possibleStartTime.plus(duration))) {
                return possibleStartTime;
            }
        }
        for (ScheduleItem item : subMap.values()) {
            if (item.getEndTime().isBefore(endTime)) {
                possibleStartTime = item.getEndTime();
                if (isAbleToAdd(possibleStartTime, possibleStartTime.plus(duration))) {
                    return possibleStartTime;
                }
            }
        }
        return null;
    }


    private boolean isAbleToAdd(Event item) {
        return isAbleToAdd(item.getStartTime(), item.getEndTime());
    }


    private boolean isAbleToAdd(LocalDateTime startTime, LocalDateTime endTime) {
        Map.Entry<LocalDateTime, ScheduleItem> lowerEntry = schedule.floorEntry(startTime);
        Map.Entry<LocalDateTime, ScheduleItem> higherEntry = schedule.ceilingEntry(startTime);

        if (lowerEntry != null && lowerEntry.getValue().getEndTime().isAfter(startTime))
            return false;

        if (higherEntry != null && higherEntry.getValue().getStartTime().isBefore(endTime))
            return false;

        return true;
    }

    private void add(Event event) {
        ScheduleItem item = new ScheduleItem(event);
        schedule.put(item.getStartTime(), item);
    }

    public void add(Event event, LocalDateTime startTime, LocalDateTime endTime) {
        ScheduleItem item = new ScheduleItem(event, startTime, endTime);
        schedule.put(item.getStartTime(), item);
    }
}
