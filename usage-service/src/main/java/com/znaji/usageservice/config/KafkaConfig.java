package com.znaji.usageservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic energyReadingIngestedTopic(
            @Value("${app.kafka.topics.energy-reading-ingested}") String topicName
    ) {
        return new NewTopic(topicName, 1, (short) 1);
    }

    @Bean
    public NewTopic energyAnomalyDetectedTopic(
            @Value("${app.kafka.topics.user-usage-threshold-exceeded}") String topicName
    ) {
        return new NewTopic(topicName, 1, (short) 1);
    }
}
