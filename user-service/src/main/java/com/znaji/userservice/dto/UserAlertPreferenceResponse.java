package com.znaji.userservice.dto;

public record UserAlertPreferenceResponse(
        Long userId,
        Boolean alertingEnabled,
        Double energyAlertThreshold
) {
}