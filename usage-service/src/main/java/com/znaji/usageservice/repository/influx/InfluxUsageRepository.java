package com.znaji.usageservice.repository.influx;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;
import com.znaji.usageservice.config.InfluxProperties;
import com.znaji.usageservice.domain.UsageReading;
import com.znaji.usageservice.dto.UsageAggregateResponse;
import com.znaji.usageservice.event.EnergyReadingIngestedEvent;
import com.znaji.usageservice.repository.UsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import java.time.Instant;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InfluxUsageRepository implements UsageRepository {

    private static final String MEASUREMENT = "energy_reading";

    private final InfluxDBClient influxDBClient;
    private final InfluxProperties influxProperties;

    @Override
    public void saveReading(EnergyReadingIngestedEvent event) {
        Point point = Point.measurement(MEASUREMENT)
                .addTag("deviceId", String.valueOf(event.deviceId()))
                .addTag("userId", String.valueOf(event.userId()))
                .addTag("deviceName", nullSafe(event.deviceName()))
                .addTag("deviceType", nullSafe(event.deviceType()))
                .addTag("location", nullSafe(event.location()))
                .addField("powerWatts", event.powerWatts())
                .time(event.timestamp(), WritePrecision.MS);

        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        writeApi.writePoint(influxProperties.bucket(), influxProperties.org(), point);
    }

    @Override
    public List<UsageReading> findByDeviceIdAndRange(String deviceId, Instant from, Instant to) {
        String flux = """
                from(bucket: "%s")
                  |> range(start: time(v: "%s"), stop: time(v: "%s"))
                  |> filter(fn: (r) => r["_measurement"] == "%s")
                  |> filter(fn: (r) => r["_field"] == "powerWatts")
                  |> filter(fn: (r) => r["deviceId"] == "%s")
                  |> sort(columns: ["_time"])
                """.formatted(
                influxProperties.bucket(),
                from,
                to,
                MEASUREMENT,
                deviceId
        );
        return queryUsageReadings(flux);
    }

    @Override
    public List<UsageReading> findByUserIdAndRange(Long userId, Instant from, Instant to) {
        String flux = """
                from(bucket: "%s")
                  |> range(start: time(v: "%s"), stop: time(v: "%s"))
                  |> filter(fn: (r) => r["_measurement"] == "%s")
                  |> filter(fn: (r) => r["_field"] == "powerWatts")
                  |> filter(fn: (r) => r["userId"] == "%s")
                  |> sort(columns: ["_time"])
                """.formatted(
                influxProperties.bucket(),
                from,
                to,
                MEASUREMENT,
                userId
        );
        return queryUsageReadings(flux);
    }

    @Override
    public List<UsageReading> findAllByRange(Instant from, Instant to) {
        String flux = """
                from(bucket: "%s")
                  |> range(start: time(v: "%s"), stop: time(v: "%s"))
                  |> filter(fn: (r) => r["_measurement"] == "%s")
                  |> filter(fn: (r) => r["_field"] == "powerWatts")
                  |> sort(columns: ["_time"])
                """.formatted(
                influxProperties.bucket(),
                from,
                to,
                MEASUREMENT
        );
        return queryUsageReadings(flux);
    }

    @Override
    public List<UsageAggregateResponse> findHourlyAverageByDeviceIdAndRange(String deviceId, Instant from, Instant to) {
        String flux = """
                from(bucket: "%s")
                  |> range(start: time(v: "%s"), stop: time(v: "%s"))
                  |> filter(fn: (r) => r["_measurement"] == "%s")
                  |> filter(fn: (r) => r["_field"] == "powerWatts")
                  |> filter(fn: (r) => r["deviceId"] == "%s")
                  |> aggregateWindow(every: 1h, fn: mean, createEmpty: false)
                  |> sort(columns: ["_time"])
                """.formatted(
                influxProperties.bucket(),
                from,
                to,
                MEASUREMENT,
                deviceId
        );
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(flux, influxProperties.org());
        return tables.stream()
                .flatMap(table -> table.getRecords().stream())
                .map(record -> new UsageAggregateResponse(
                        record.getTime(),
                        ((Number) record.getValue()).doubleValue()
                ))
                .toList();

    }

    @Override
    public Double findAveragePowerByUserIdAndRange(Long userId, Instant from, Instant to) {
        String flux = """
            from(bucket: "%s")
              |> range(start: time(v: "%s"), stop: time(v: "%s"))
              |> filter(fn: (r) => r["_measurement"] == "%s")
              |> filter(fn: (r) => r["_field"] == "powerWatts")
              |> filter(fn: (r) => r["userId"] == "%s")
              |> mean()
            """.formatted(
                influxProperties.bucket(),
                from,
                to,
                MEASUREMENT,
                userId
        );
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(flux, influxProperties.org());
        return tables.stream()
                .flatMap(table -> table.getRecords().stream())
                .map(record -> ((Number) record.getValue()).doubleValue())
                .findFirst()
                .orElse(0.0);
    }

    private List<UsageReading> queryUsageReadings(String flux) {
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(flux, influxProperties.org());
        return tables.stream()
                .flatMap(table -> table.getRecords().stream())
                .map(record -> new UsageReading(
                        Long.valueOf((String) record.getValueByKey("deviceId")),
                        Long.valueOf((String) record.getValueByKey("userId")),
                        (String) record.getValueByKey("deviceName"),
                        (String) record.getValueByKey("deviceType"),
                        (String) record.getValueByKey("location"),
                        record.getTime(),
                        ((Number) record.getValue()).doubleValue()
                ))
                .toList();
    }

    private String nullSafe(String s) {
        return s != null ? s : "";
    }
}
