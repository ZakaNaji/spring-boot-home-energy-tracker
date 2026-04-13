package com.znaji.deviceservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateDeviceRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must not exceed 255 characters")
        String name,

        @Size(max = 50, message = "Type must not exceed 50 characters")
        String type,

        @Size(max = 255, message = "Location must not exceed 255 characters")
        String location,

        @NotNull(message = "User id is required")
        Long userId
) {
}