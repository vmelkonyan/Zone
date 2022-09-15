package com.codulate.zone.mapper;

import com.codulate.zone.data.ZoneDto;
import com.codulate.zone.entity.Zone;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", config = IgnoreUnmappedMapperConfig.class)
public interface ZoneMapper {
    ZoneDto map(Zone zone);

    Zone map(ZoneDto zoneDto);

    List<ZoneDto> mapZoneDtoList(List<Zone> zoneList);
}
