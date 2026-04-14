package com.znaji.simulatorservice.dto;

import java.time.Instant;

public record EnergyReadingRequest(
        Long deviceId,
        Instant timestamp,
        Double powerWatts
) {
}