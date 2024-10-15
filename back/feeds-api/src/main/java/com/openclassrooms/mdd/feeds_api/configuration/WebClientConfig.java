package com.openclassrooms.mdd.feeds_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient configuration
 */
@Configuration
public class WebClientConfig {

    /**
     * Instantiate a Webclient
     * @param builder Webclient builder
     * @return A Webclient
     */
    @Bean
    public WebClient webclient(WebClient.Builder builder) {
        return builder
        .build();
    }
}
