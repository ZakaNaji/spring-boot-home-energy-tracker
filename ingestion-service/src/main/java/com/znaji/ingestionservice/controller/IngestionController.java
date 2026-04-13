package com.znaji.ingestionservice.controller;

import com.znaji.ingestionservice.dto.EnergyReadingRequest;
import com.znaji.ingestionservice.dto.IngestionResponse;
import com.znaji.ingestionservice.service.IngestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/readings")
@RequiredArgsConstructor
public class IngestionController {

    private final IngestionService ingestionService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public IngestionResponse ingestReading(@Valid @RequestBody EnergyReadingRequest request) {
        return ingestionService.ingestReading(request);
    }
}