package com.znaji.deviceservice.dto;

public record DeviceResponse(
        Long id,
        String name,
        String type,
        String location,
        Long userId
) {
}