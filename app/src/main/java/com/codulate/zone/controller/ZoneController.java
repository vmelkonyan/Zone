package com.codulate.zone.controller;

import com.codulate.zone.data.ZoneDto;
import com.codulate.zone.mapper.ZoneWebMapper;
import com.codulate.zone.ports.api.ZoneServicePort;
import com.codulate.zone.request.ZoneRequest;
import com.codulate.zone.response.ZoneResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/zone")
public class ZoneController {

    private final ZoneServicePort zoneServicePort;
    private final ZoneWebMapper zoneWebMapper;

    @PostMapping()
    public ResponseEntity<ZoneResponse> add(@Valid @RequestBody final ZoneRequest zoneRequest) {
        ZoneDto zoneDto = zoneWebMapper.map(zoneRequest);
        ZoneDto zoneSaved = zoneServicePort.addZone(zoneDto);
        return ResponseEntity.ok().body(zoneWebMapper.map(zoneSaved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneResponse> getByID(@PathVariable final long id) {
        ZoneDto zoneDto = zoneServicePort.getZoneById(id);
        return ResponseEntity.ok().body(zoneWebMapper.map(zoneDto));
    }
}
