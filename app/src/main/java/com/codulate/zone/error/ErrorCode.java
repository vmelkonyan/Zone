package com.codulate.zone.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {
    NOT_FOUND("Code"),
    CONSTRAINT("Constraint");

    @Getter
    private final String code;
}
