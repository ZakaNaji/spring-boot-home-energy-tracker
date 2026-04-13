package com.znaji.ingestionservice.event;

import java.time.Instant;

public record EnergyReadingIngestedEvent(
        String eventId,
        Long deviceId,
        Long userId,
        String deviceName,
        String deviceType,
        String location,
        Instant timestamp,
        Double powerWatts
) {
}