package com.codulate.zone.advice;

import com.codulate.zone.error.ErrorResponse;
import com.codulate.zone.exception.ZoneNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.codulate.zone.error.ErrorCode.CONSTRAINT;
import static com.codulate.zone.error.ErrorCode.NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "com.codulate.zone.controller")
public class ZoneResponseExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handler for ZoneNotFoundException.
     * If zone not found then will return 'ResponseEntity 400'.
     *
     * @param ex ZoneNotFoundException
     * @return ResponseEntity of ErrorResponse
     */
    @ResponseBody
    @ExceptionHandler(ZoneNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleZoneNotFoundException(ZoneNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), NOT_FOUND);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handle(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        String errorMessage = "";
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append(" ").append(violation.getMessage()));
            errorMessage = builder.toString();
        } else {
            errorMessage = "ConstraintViolationException occurred.";
        }
        ErrorResponse error = new ErrorResponse(errorMessage, CONSTRAINT);
        return ResponseEntity.badRequest().body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        List<ErrorResponse> errorResponseList = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorResponseList.add(new ErrorResponse(error.getField() + ": " + error.getDefaultMessage(), CONSTRAINT));
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errorResponseList.add(new ErrorResponse(error.getObjectName() + ": " + error.getDefaultMessage(), CONSTRAINT));

        }

        return ResponseEntity.badRequest().body(errorResponseList);

    }

}
