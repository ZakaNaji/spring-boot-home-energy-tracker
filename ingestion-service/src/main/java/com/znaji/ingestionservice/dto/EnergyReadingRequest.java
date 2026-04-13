package com.znaji.ingestionservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.Instant;

public record EnergyReadingRequest(

        @NotNull(message = "Device id is required")
        Long deviceId,

        @NotNull(message = "Timestamp is required")
        Instant timestamp,

        @NotNull(message = "Power watts is required")
        @PositiveOrZero(message = "Power watts must be >= 0")
        Double powerWatts
) {
}