package com.znaji.simulatorservice.client;

import com.znaji.simulatorservice.dto.EnergyReadingRequest;
import com.znaji.simulatorservice.exception.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class IngestionServiceClient {

    private final RestClient ingestionServiceRestClient;

    public void sendReading(EnergyReadingRequest request) {
        try {
            ingestionServiceRestClient.post()
                    .uri("/api/readings")
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception ex) {
            throw new ExternalServiceException(
                    "Failed to send reading to ingestion-service for deviceId=" + request.deviceId()
            );
        }
    }
}