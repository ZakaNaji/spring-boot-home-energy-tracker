package com.znaji.simulatorservice.generator;

import com.znaji.simulatorservice.dto.DeviceResponse;
import com.znaji.simulatorservice.dto.EnergyReadingRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class ReadingGenerator {

    private static final Map<String, Range> DEVICE_TYPE_RANGES = Map.ofEntries(
            Map.entry("ROUTER", new Range(8, 25)),
            Map.entry("LAMP", new Range(5, 18)),
            Map.entry("LAPTOP", new Range(35, 120)),
            Map.entry("TELEVISION", new Range(60, 220)),
            Map.entry("FRIDGE", new Range(90, 180)),
            Map.entry("MICROWAVE", new Range(800, 1400)),
            Map.entry("WASHING_MACHINE", new Range(400, 1200)),
            Map.entry("AIR_CONDITIONER", new Range(900, 2200)),
            Map.entry("SMART_AC", new Range(900, 2200)),
            Map.entry("OVEN", new Range(1500, 2600)),
            Map.entry("WATER_HEATER", new Range(1200, 2500)),
            Map.entry("HEATER", new Range(1000, 2200))
    );

    @Value("${simulator.spike-probability}")
    private double spikeProbability;

    @Value("${simulator.spike-multiplier-min}")
    private double spikeMultiplierMin;

    @Value("${simulator.spike-multiplier-max}")
    private double spikeMultiplierMax;

    public EnergyReadingRequest generate(DeviceResponse device) {
        Range range = DEVICE_TYPE_RANGES.getOrDefault(
                safeUpper(device.type()),
                new Range(50, 300)
        );

        double value = randomDouble(range.min(), range.max());

        if (ThreadLocalRandom.current().nextDouble() < spikeProbability) {
            double multiplier = randomDouble(spikeMultiplierMin, spikeMultiplierMax);
            value = value * multiplier;
        }

        value = Math.round(value * 10.0) / 10.0;

        return new EnergyReadingRequest(
                device.id(),
                Instant.now(),
                value
        );
    }

    private String safeUpper(String value) {
        return value == null ? "" : value.toUpperCase();
    }

    private double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    private record Range(double min, double max) {
    }
}