package com.znaji.ingestionservice.client;

import com.znaji.ingestionservice.dto.DeviceResponse;
import com.znaji.ingestionservice.exception.DeviceNotFoundException;
import com.znaji.ingestionservice.exception.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
@RequiredArgsConstructor
public class DeviceServiceClient {

    private final RestClient deviceServiceRestClient;

    public DeviceResponse getDeviceById(Long deviceId) {
        try {
            return deviceServiceRestClient.get()
                    .uri("/api/devices/{id}", deviceId)
                    .retrieve()
                    .body(DeviceResponse.class);
        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new DeviceNotFoundException(deviceId);
            }
            throw new ExternalServiceException(
                    "Failed to validate device existence. deviceId=" + deviceId + ", status=" + ex.getStatusCode()
            );
        } catch (Exception ex) {
            throw new ExternalServiceException(
                    "Failed to communicate with device-service for deviceId=" + deviceId
            );
        }
    }
}
