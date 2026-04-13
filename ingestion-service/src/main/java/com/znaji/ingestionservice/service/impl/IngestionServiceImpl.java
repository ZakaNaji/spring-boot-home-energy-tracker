package com.znaji.ingestionservice.service.impl;

import com.znaji.ingestionservice.client.DeviceServiceClient;
import com.znaji.ingestionservice.dto.DeviceResponse;
import com.znaji.ingestionservice.dto.EnergyReadingRequest;
import com.znaji.ingestionservice.dto.IngestionResponse;
import com.znaji.ingestionservice.event.EnergyReadingIngestedEvent;
import com.znaji.ingestionservice.kafka.EnergyReadingProducer;
import com.znaji.ingestionservice.service.IngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IngestionServiceImpl implements IngestionService {

    private final DeviceServiceClient deviceServiceClient;
    private final EnergyReadingProducer energyReadingProducer;

    @Override
    public IngestionResponse ingestReading(EnergyReadingRequest request) {
        DeviceResponse device = deviceServiceClient.getDeviceById(request.deviceId());

        String eventId = UUID.randomUUID().toString();

        EnergyReadingIngestedEvent event = new EnergyReadingIngestedEvent(
                eventId,
                device.id(),
                device.userId(),
                device.name(),
                device.type(),
                device.location(),
                request.timestamp(),
                request.powerWatts()
        );

        energyReadingProducer.publish(event);

        return new IngestionResponse(
                "Reading ingested successfully",
                eventId
        );
    }
}