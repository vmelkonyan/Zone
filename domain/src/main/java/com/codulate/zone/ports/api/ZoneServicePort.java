package com.codulate.zone.ports.api;

import com.codulate.zone.data.CoordinateDto;
import com.codulate.zone.data.ZoneDto;

import java.util.List;

public interface ZoneServicePort {
    ZoneDto addZone(ZoneDto zoneDto);

    ZoneDto getZoneById(Long zoneId);

    List<ZoneDto> getAllZone();
}
