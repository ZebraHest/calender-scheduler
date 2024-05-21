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
        System.out.println("VALIDATE");

        if (event.isRepeating() && CollectionUtils.isEmpty(event.getRepeatDays())) {
            errors.rejectValue("repeatDays", "RepeatDays can not be empty is event is repating");
        }

    }
}
