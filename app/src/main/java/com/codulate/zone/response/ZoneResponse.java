package com.codulate.zone.response;

import com.codulate.zone.data.CoordinateDto;

import java.util.Set;

public record ZoneResponse(Long id,
                           String name,
                           Set<CoordinateDto> coordinates) {
}
