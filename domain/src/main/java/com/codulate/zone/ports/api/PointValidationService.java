package com.codulate.zone.ports.api;

import com.codulate.zone.data.CoordinateDto;
import com.codulate.zone.data.ZoneDto;

public interface PointValidationService {
    boolean validate(ZoneDto zoneDto, CoordinateDto vPoint);
}
