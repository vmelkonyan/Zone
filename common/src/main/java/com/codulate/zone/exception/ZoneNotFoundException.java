package com.codulate.zone.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ZoneNotFoundException extends RuntimeException {

    @Getter
    private final String message;
}
