package com.znaji.simulatorservice.dto;

public record DeviceResponse(
        Long id,
        String name,
        String type,
        String location,
        Long userId
) {
}