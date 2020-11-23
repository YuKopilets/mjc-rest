package com.epam.esm.entity.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DaysDurationValidator.class)
@Target(ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ValidDaysDuration {
    String message() default "{days.duration.default.massage}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
