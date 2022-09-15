package com.codulate.zone.service;

import com.codulate.zone.data.ZoneDto;
import com.codulate.zone.ports.api.ZoneServicePort;
import com.codulate.zone.ports.spi.ZonePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ZoneServiceImpl implements ZoneServicePort {

    private final ZonePersistencePort zonePersistencePort;

    @Override
    public ZoneDto addZone(ZoneDto zoneDto) {
        return zonePersistencePort.addZone(zoneDto);
    }

    @Override
    public ZoneDto getZoneById(Long zoneId) {
        return zonePersistencePort.getZoneById(zoneId);
    }

    @Override
    public List<ZoneDto> getAllZone() {
        return zonePersistencePort.getAllZoneDTO();
    }
}
