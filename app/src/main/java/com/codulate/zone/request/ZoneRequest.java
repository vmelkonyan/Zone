package com.codulate.zone.request;

import com.codulate.zone.data.CoordinateDto;
import com.codulate.zone.validator.constraint.MinSizeConstraint;

import javax.validation.constraints.Size;
import java.util.Set;

public record ZoneRequest(
    @Size(min = 3)
    String name,
    @MinSizeConstraint
    Set<CoordinateDto> coordinates) {
}
