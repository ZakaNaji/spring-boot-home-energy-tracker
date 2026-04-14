package com.znaji.simulatorservice.dto;

public record SimulationResponse(
        String message,
        int readingsSent
) {
}