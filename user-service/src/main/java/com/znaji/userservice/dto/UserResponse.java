package com.znaji.userservice.dto;

public record UserResponse(
        Long id,
        String name,
        String surname,
        String email,
        String address,
        Boolean alertingEnabled,
        Double energyAlertThreshold
) {
}