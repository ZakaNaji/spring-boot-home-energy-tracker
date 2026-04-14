package com.znaji.simulatorservice.service.impl;

import com.znaji.simulatorservice.client.DeviceServiceClient;
import com.znaji.simulatorservice.client.IngestionServiceClient;
import com.znaji.simulatorservice.dto.DeviceResponse;
import com.znaji.simulatorservice.dto.EnergyReadingRequest;
import com.znaji.simulatorservice.dto.SimulationResponse;
import com.znaji.simulatorservice.generator.ReadingGenerator;
import com.znaji.simulatorservice.service.SimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class SimulationServiceImpl implements SimulationService {

    private final DeviceServiceClient deviceServiceClient;
    private final IngestionServiceClient ingestionServiceClient;
    private final ReadingGenerator readingGenerator;

    @Override
    public SimulationResponse runOnceForAllDevices() {
        List<DeviceResponse> devices = deviceServiceClient.getAllDevices();

        int sent = 0;
        for (DeviceResponse device : devices) {
            EnergyReadingRequest request = readingGenerator.generate(device);
            ingestionServiceClient.sendReading(request);
            sent++;
        }

        return new SimulationResponse(
                "Simulation run completed for all devices",
                sent
        );
    }

    @Override
    public SimulationResponse runBatch(int count) {
        List<DeviceResponse> devices = deviceServiceClient.getAllDevices();

        if (devices.isEmpty()) {
            return new SimulationResponse("No devices available for simulation", 0);
        }

        Collections.shuffle(devices);
        int actualCount = Math.min(count, devices.size());

        int sent = 0;
        for (int i = 0; i < actualCount; i++) {
            EnergyReadingRequest request = readingGenerator.generate(devices.get(i));
            ingestionServiceClient.sendReading(request);
            sent++;
        }

        return new SimulationResponse(
                "Simulation batch completed",
                sent
        );
    }

    @Override
    public SimulationResponse injectSpike(Long deviceId) {

        DeviceResponse device = deviceServiceClient.getDeviceById(deviceId);

        double base = ThreadLocalRandom.current().nextDouble(2000, 5000);

        EnergyReadingRequest request = new EnergyReadingRequest(
                device.id(),
                Instant.now(),
                Math.round(base * 10.0) / 10.0
        );

        ingestionServiceClient.sendReading(request);

        return new SimulationResponse(
                "Spike injected successfully for device " + deviceId,
                1
        );
    }
}