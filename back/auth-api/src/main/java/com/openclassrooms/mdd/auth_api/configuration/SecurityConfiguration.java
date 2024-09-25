package com.openclassrooms.mdd.auth_api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.openclassrooms.mdd.auth_api.service.UserServiceImpl;

/**
 * Security configuration.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    private ReactiveUserDetailsService userDetailsService;
    private final RsaKeyProperties rsaKeys;

    @Autowired
    public SecurityConfiguration(
            UserServiceImpl userDetailsService,
            RsaKeyProperties rsaKeys) {
        this.userDetailsService = userDetailsService;
        this.rsaKeys = rsaKeys;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
