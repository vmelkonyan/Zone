package com.codulate.zone.validator;

import com.codulate.zone.data.CoordinateDto;
import com.codulate.zone.validator.constraint.MinSizeConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class MinSizeConstraintValidator implements ConstraintValidator<MinSizeConstraint, Set<CoordinateDto>> {

    @Override
    public boolean isValid(Set<CoordinateDto> values, ConstraintValidatorContext context) {
        return values.size() >= 3;
    }
}
