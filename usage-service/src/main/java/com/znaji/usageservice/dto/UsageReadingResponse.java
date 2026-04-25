package com.znaji.usageservice.dto;

import java.time.Instant;

public record UsageReadingResponse(
        Long deviceId,
        Long userId,
        String deviceName,
        String deviceType,
        String location,
        Instant timestamp,
        Double powerWatts
) {
}