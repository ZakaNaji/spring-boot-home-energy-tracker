package com.znaji.usageservice.event;

import java.time.Instant;

public record UserUsageThresholdExceededEvent(
        String eventId,
        Long userId,
        Instant windowStart,
        Instant windowEnd,
        Double averagePowerWatts,
        Double threshold,
        String reason,
        String severity
) {
}