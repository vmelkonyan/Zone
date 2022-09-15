package com.codulate.zone.validator.constraint;

import com.codulate.zone.validator.MinSizeConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = MinSizeConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinSizeConstraint {

    String message() default "The input list cannot contain les than 3 coordinates.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
