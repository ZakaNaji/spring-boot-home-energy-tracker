package com.znaji.ingestionservice.service;

import com.znaji.ingestionservice.dto.EnergyReadingRequest;
import com.znaji.ingestionservice.dto.IngestionResponse;

public interface IngestionService {

    IngestionResponse ingestReading(EnergyReadingRequest request);
}