package com.znaji.usageservice.dto;

public record UserAlertPreferenceResponse(
        Long userId,
        Boolean alertingEnabled,
        Double energyAlertThreshold
) {
}