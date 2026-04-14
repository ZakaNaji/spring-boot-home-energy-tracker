package com.znaji.simulatorservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient deviceServiceRestClient(
            RestClient.Builder builder,
            @Value("${device-service.base-url}") String deviceServiceBaseUrl
    ) {
        return builder.baseUrl(deviceServiceBaseUrl).build();
    }

    @Bean
    public RestClient ingestionServiceRestClient(
            RestClient.Builder builder,
            @Value("${ingestion-service.base-url}") String ingestionServiceBaseUrl
    ) {
        return builder.baseUrl(ingestionServiceBaseUrl).build();
    }
}