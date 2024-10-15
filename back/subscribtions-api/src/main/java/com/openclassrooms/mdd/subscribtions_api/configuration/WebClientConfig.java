package com.openclassrooms.mdd.subscribtions_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient configuration
 */
@Configuration
public class WebClientConfig {

    /**
     * Instantiate a WebClient
     *
     * @param builder WebClient builder
     * @return A webclient
     */
    @Bean
    public WebClient webclient(WebClient.Builder builder) {
        return builder
        .build();
    }
}
