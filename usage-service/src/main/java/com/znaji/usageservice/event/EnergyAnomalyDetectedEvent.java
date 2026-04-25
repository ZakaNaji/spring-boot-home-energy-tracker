package com.znaji.usageservice.event;

import java.time.Instant;

public record EnergyAnomalyDetectedEvent(
        String eventId,
        Long deviceId,
        Long userId,
        String deviceName,
        String deviceType,
        String location,
        Instant timestamp,
        Double powerWatts,
        Double threshold,
        String reason,
        String severity
) {
}