package com.znaji.simulatorservice.client;

import com.znaji.simulatorservice.dto.DeviceResponse;
import com.znaji.simulatorservice.exception.DeviceNotFoundException;
import com.znaji.simulatorservice.exception.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeviceServiceClient {

    private final RestClient deviceServiceRestClient;

    public List<DeviceResponse> getAllDevices() {
        try {
            List<DeviceResponse> devices = deviceServiceRestClient.get()
                    .uri("/api/devices")
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<DeviceResponse>>() {
                    });

            return devices == null ? List.of() : devices;
        } catch (Exception ex) {
            throw new ExternalServiceException("Failed to fetch devices from device-service");
        }
    }

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
            throw new ExternalServiceException("Failed to fetch device with id=" + deviceId + " from device-service");
        } catch (Exception ex) {
            throw new ExternalServiceException("Failed to fetch device with id=" + deviceId + " from device-service");
        }
    }
}