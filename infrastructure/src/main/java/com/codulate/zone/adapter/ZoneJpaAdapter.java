package com.codulate.zone.adapter;

import com.codulate.zone.data.ZoneDto;
import com.codulate.zone.entity.Coordinate;
import com.codulate.zone.entity.Zone;
import com.codulate.zone.exception.ZoneNotFoundException;
import com.codulate.zone.mapper.ZoneMapper;
import com.codulate.zone.ports.spi.ZonePersistencePort;
import com.codulate.zone.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class ZoneJpaAdapter implements ZonePersistencePort {

    private final ZoneRepository zoneRepository;
    private final ZoneMapper zoneMapper;

    @Override
    public ZoneDto addZone(ZoneDto zoneDto) {
        Zone zone = zoneMapper.map(zoneDto);

        for (Coordinate coordinate : zone.getCoordinates()) {
            coordinate.setZone(zone);
        }

        Zone save = zoneRepository.save(zone);
        return zoneMapper.map(save);
    }

    @Override
    public ZoneDto getZoneById(Long zoneId) {
        Zone zone = getZone(zoneId);
        return zoneMapper.map(zone);
    }

    @Override
    public List<ZoneDto> getAllZoneDTO() {
        List<Zone> zones = getAllZone();

        return zoneMapper.mapZoneDtoList(zones);
    }

    @Transactional(readOnly = true)
    private Zone getZone(Long id) {
        return zoneRepository.findById(id).orElseThrow(() -> {
            log.warn("Zone not found with id - {}", id);
            throw new ZoneNotFoundException("Zone not found with id " + id);
        });
    }

    @Transactional(readOnly = true)
    private List<Zone> getAllZone() {
        return zoneRepository.findAll();
    }
}
