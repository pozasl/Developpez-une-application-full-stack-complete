package com.openclassrooms.mdd.subscribtions_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webclient(WebClient.Builder builder) {
        return builder
        .build();
    }
}
