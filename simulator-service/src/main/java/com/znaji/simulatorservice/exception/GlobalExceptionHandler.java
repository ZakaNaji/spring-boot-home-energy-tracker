package com.znaji.simulatorservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExternalServiceException.class)
    public org.springframework.http.ResponseEntity<ErrorResponse> handleExternalService(
            ExternalServiceException ex,
            HttpServletRequest request
    ) {
        log.error("External service error on path={}", request.getRequestURI(), ex);

        return buildErrorResponse(ex, request, HttpStatus.SERVICE_UNAVAILABLE, null);
    }

    @ExceptionHandler(Exception.class)
    public org.springframework.http.ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error("Unexpected error on path={}", request.getRequestURI(), ex);

        return buildErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(DeviceNotFoundException.class)
    public org.springframework.http.ResponseEntity<ErrorResponse> handleDeviceNotFound(
            DeviceNotFoundException ex,
            HttpServletRequest request
    ) {
        return buildErrorResponse(ex, request, HttpStatus.BAD_REQUEST, null);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpServletRequest request, HttpStatus status, Map<String, String> validationErrors) {
        var error =  new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                validationErrors
        );

        return ResponseEntity.status(status).body(error);
    }
}