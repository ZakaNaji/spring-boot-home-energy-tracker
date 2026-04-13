package com.znaji.deviceservice.client;

import com.znaji.deviceservice.exception.ExternalServiceException;
import com.znaji.deviceservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private final RestClient userServiceRestClient;

    public void validateUserExists(Long userId) {
        try {
            userServiceRestClient.get()
                    .uri("/api/users/{id}", userId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UserNotFoundException(userId);
            }
            throw new ExternalServiceException(
                    "Failed to validate user existence. userId=" + userId + ", status=" + ex.getStatusCode()
            );
        } catch (Exception ex) {
            throw new ExternalServiceException(
                    "Failed to communicate with user-service for userId=" + userId
            );
        }
    }
}