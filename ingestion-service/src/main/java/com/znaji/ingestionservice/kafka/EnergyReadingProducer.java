package com.znaji.ingestionservice.kafka;

import com.znaji.ingestionservice.event.EnergyReadingIngestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnergyReadingProducer {

    private final KafkaTemplate<String, EnergyReadingIngestedEvent> kafkaTemplate;

    @Value("${app.kafka.topics.energy-reading-ingested}")
    private String topicName;

    public void publish(EnergyReadingIngestedEvent event) {
        kafkaTemplate.send(topicName, event.deviceId().toString(), event);
        log.info("Published energy reading event. eventId={} deviceId={} topic={}",
                event.eventId(), event.deviceId(), topicName);
    }
}
