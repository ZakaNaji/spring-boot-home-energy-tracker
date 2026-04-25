package com.znaji.usageservice.kafka;

import com.znaji.usageservice.event.UserUsageThresholdExceededEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserUsageThresholdExceededProducer {

    private final KafkaTemplate<String, UserUsageThresholdExceededEvent> kafkaTemplate;
    @Value("${app.kafka.topics.user-usage-threshold-exceeded}")
    private String topicName;

    public void publish(UserUsageThresholdExceededEvent event) {
        log.info("Publishing UserUsageThresholdExceededEvent: {}", event);
        kafkaTemplate.send(topicName, event.userId().toString(), event);
    }

}
