package com.znaji.usageservice.dto;

import java.time.Instant;

public record UsageAggregateResponse(
        Instant bucketStart,
        Double averagePowerWatts
) {
}