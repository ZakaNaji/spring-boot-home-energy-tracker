package com.znaji.userservice.mapper;


import com.znaji.userservice.domain.User;
import com.znaji.userservice.dto.CreateUserRequest;
import com.znaji.userservice.dto.UpdateUserRequest;
import com.znaji.userservice.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequest request) {
        return User.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .address(request.address())
                .alertingEnabled(request.alertingEnabled())
                .energyAlertThreshold(request.energyAlertThreshold())
                .build();
    }

    public void updateEntity(User user, UpdateUserRequest request) {
        user.setName(request.name());
        user.setSurname(request.surname());
        user.setEmail(request.email());
        user.setAddress(request.address());
        user.setAlertingEnabled(request.alertingEnabled());
        user.setEnergyAlertThreshold(request.energyAlertThreshold());
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getAddress(),
                user.getAlertingEnabled(),
                user.getEnergyAlertThreshold()
        );
    }
}