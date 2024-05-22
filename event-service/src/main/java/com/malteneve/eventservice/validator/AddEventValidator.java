package com.malteneve.eventservice.validator;

import com.malteneve.eventservice.domain.Event;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddEventValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Event.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Event event = (Event) target;

        if (event.isRepeating()
                && CollectionUtils.isEmpty(event.getRepeatDays())) {
            errors.rejectValue("repeatDays", "400", "RepeatDays can not be empty if event is repating");
        }

        if (event.isRepeating()
                && event.getRepeatStartDate() == null) {
            errors.rejectValue("RepeatStartDate", "400", "RepeatStartDate can not be null if event is repating");
        }

        if (event.isRepeating()
                && event.getRepeatEndDate() == null) {
            errors.rejectValue("RepeatEndDate", "400", "RepeatEndDate can not be null if event is repating");
        }

        if (event.getRepeatStartDate() != null
                && event.getRepeatEndDate() != null
                && event.getRepeatStartDate().isAfter(event.getRepeatEndDate())) {
            errors.rejectValue("RepeatEndDate", "400", "RepeatEndDate must be after RepeatStartDate");
        }

        if (event.getStartTime() != null
                && event.getEndTime() != null
                && event.getStartTime().isAfter(event.getEndTime())) {
            errors.rejectValue("EndTime", "400", "EndTime must be after StartTime");
        }

        if (event.isFlexible()
                && (event.getDuration() == null
                || event.getDuration().isZero())) {
            errors.rejectValue("Duration", "400", "Duration can not be null if event is flexible");
        }
    }
}
