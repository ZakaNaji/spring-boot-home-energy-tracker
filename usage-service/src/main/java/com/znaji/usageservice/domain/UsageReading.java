package com.znaji.usageservice.domain;

import java.time.Instant;

public record UsageReading(
        Long deviceId,
        Long userId,
        String deviceName,
        String deviceType,
        String location,
        Instant timestamp,
        Double powerWatts
) {
}