package com.znaji.usageservice.client;

import com.znaji.usageservice.dto.UserAlertPreferenceResponse;
import com.znaji.usageservice.exception.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private final RestClient userClient;

    public List<UserAlertPreferenceResponse> getEnabledAlertPreferences() {
        try {
            List<UserAlertPreferenceResponse> responses = userClient.get()
                    .uri("/api/users/alert-preferences/enabled")
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<UserAlertPreferenceResponse>>() {});
            return responses != null ? responses : List.of();
        } catch (Exception e) {
            throw new ExternalServiceException("Failed to fetch enabled alert preferences from user-service");
        }
    }
}
