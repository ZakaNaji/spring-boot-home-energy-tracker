package com.znaji.simulatorservice.controller;

import com.znaji.simulatorservice.dto.SimulationResponse;
import com.znaji.simulatorservice.service.SimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simulation")
@RequiredArgsConstructor
public class SimulationController {

    private final SimulationService simulationService;

    @PostMapping("/run-once")
    public SimulationResponse runOnce() {
        return simulationService.runOnceForAllDevices();
    }

    @PostMapping("/run-batch")
    public SimulationResponse runBatch(@RequestParam(defaultValue = "10") int count) {
        return simulationService.runBatch(count);
    }

    @PostMapping("/spike")
    public SimulationResponse injectSpike(@RequestParam Long deviceId) {
        return simulationService.injectSpike(deviceId);
    }
}