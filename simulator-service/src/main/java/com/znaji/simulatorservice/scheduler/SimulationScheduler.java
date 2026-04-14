package com.znaji.simulatorservice.scheduler;

import com.znaji.simulatorservice.service.SimulationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimulationScheduler {

    private final SimulationService simulationService;

    @Value("${simulator.enabled}")
    private boolean simulatorEnabled;

    @Value("${simulator.devices-per-cycle}")
    private int devicesPerCycle;

    @Scheduled(fixedDelayString = "${simulator.interval-ms}")
    public void runScheduledSimulation() {
        if (!simulatorEnabled) {
            return;
        }

        var response = simulationService.runBatch(devicesPerCycle);
        log.info("Scheduled simulation completed. readingsSent={}", response.readingsSent());
    }
}