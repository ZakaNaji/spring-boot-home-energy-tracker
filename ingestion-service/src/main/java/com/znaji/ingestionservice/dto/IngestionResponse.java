package com.znaji.ingestionservice.dto;

public record IngestionResponse(
        String message,
        String eventId
) {
}