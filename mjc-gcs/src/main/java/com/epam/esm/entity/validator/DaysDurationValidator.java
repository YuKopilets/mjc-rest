package com.epam.esm.entity.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;

/**
 * Implements the validation logic for validation of days Duration.
 * Validate that Duration holds value in positive number of days.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see ConstraintValidator
 */
public class DaysDurationValidator implements ConstraintValidator<ValidDaysDuration, Duration> {
    @Override
    public boolean isValid(Duration duration, ConstraintValidatorContext context) {
        return duration == null || duration.toDays() > 0;
    }
}
