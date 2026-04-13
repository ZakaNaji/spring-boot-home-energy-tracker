package com.znaji.ingestionservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient deviceServiceRestClient(
            @Value("${device-service.base-url}") String deviceServiceUrl,
            RestClient.Builder builder
    ) {
        return builder
                .baseUrl(deviceServiceUrl)
                .build();
    }
}
