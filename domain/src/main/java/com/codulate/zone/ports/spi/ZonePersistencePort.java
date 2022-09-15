package com.codulate.zone.ports.spi;

import com.codulate.zone.data.ZoneDto;

import java.util.List;

public interface ZonePersistencePort {
    ZoneDto addZone(ZoneDto zoneDto);

    ZoneDto getZoneById(Long zoneId);

    List<ZoneDto> getAllZoneDTO();
}
