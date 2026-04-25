package com.znaji.usageservice.repository;

import com.znaji.usageservice.domain.UsageReading;
import com.znaji.usageservice.dto.UsageAggregateResponse;
import com.znaji.usageservice.event.EnergyReadingIngestedEvent;

import java.time.Instant;
import java.util.List;

public interface UsageRepository {

    void saveReading(EnergyReadingIngestedEvent event);

    List<UsageReading> findByDeviceIdAndRange(String deviceId, Instant from, Instant to);

    List<UsageReading> findByUserIdAndRange(Long userId, Instant from, Instant to);

    List<UsageReading> findAllByRange(Instant from, Instant to);

    List<UsageAggregateResponse> findHourlyAverageByDeviceIdAndRange(String deviceId, Instant from, Instant to);

    Double findAveragePowerByUserIdAndRange(Long userId, Instant from, Instant to);


}
