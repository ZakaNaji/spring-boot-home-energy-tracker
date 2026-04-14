package com.znaji.simulatorservice.service;

import com.znaji.simulatorservice.dto.SimulationResponse;

public interface SimulationService {

    SimulationResponse runOnceForAllDevices();

    SimulationResponse runBatch(int count);

    SimulationResponse injectSpike(Long deviceId);
}