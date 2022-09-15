package com.codulate.zone.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ZoneDto {
    private Long id;

    private String name;

    private Set<CoordinateDto> coordinates;
}
