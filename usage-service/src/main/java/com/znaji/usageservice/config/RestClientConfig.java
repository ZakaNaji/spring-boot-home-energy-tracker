package com.znaji.usageservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient userServiceRestClient(RestClient.Builder builder,
                                            @Value("${user-service.base-url}") String userServiceBaseUrl) {
        return builder
                .baseUrl(userServiceBaseUrl)
                .build();
    }
}
