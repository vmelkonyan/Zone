package com.codulate.zone.ports.spi;

import com.codulate.zone.data.CoordinateDto;
import com.codulate.zone.data.ZoneDto;

public interface PointValidatePort {
    boolean validatePoint(ZoneDto zoneDto, CoordinateDto vPoint);
}
