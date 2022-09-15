package com.codulate.zone.mapper;

import com.codulate.zone.data.ZoneDto;
import com.codulate.zone.request.ZoneRequest;
import com.codulate.zone.response.ZoneResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = IgnoreUnmappedMapperConfig.class)
public interface ZoneWebMapper {
    ZoneDto map(ZoneRequest zoneRequest);

    ZoneResponse map(ZoneDto zoneDto);
}
