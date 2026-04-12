package com.znaji.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        String name,

        @Size(max = 100, message = "Surname must not exceed 100 characters")
        String surname,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 255, message = "Email must not exceed 255 characters")
        String email,

        String address,

        @NotNull(message = "Alerting enabled flag is required")
        Boolean alertingEnabled,

        @NotNull(message = "Energy alert threshold is required")
        @PositiveOrZero(message = "Energy alert threshold must be >= 0")
        Double energyAlertThreshold
) {
}
