package com.codulate.zone.service;

import com.codulate.zone.data.CoordinateDto;
import com.codulate.zone.data.ZoneDto;
import com.codulate.zone.ports.api.PointValidationService;
import com.codulate.zone.ports.spi.PointValidatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointValidationServiceImpl implements PointValidationService {

    private final PointValidatePort pointValidatePort;

    @Override
    public boolean validate(ZoneDto zoneDto, CoordinateDto vPoint) {
        return pointValidatePort.validatePoint(zoneDto, vPoint);
    }
}
