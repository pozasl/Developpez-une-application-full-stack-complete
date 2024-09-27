package com.openclassrooms.mdd.auth_api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.openclassrooms.mdd.common.configuration.RsaKeyProperties;
import com.openclassrooms.mdd.common.configuration.SecurityConfiguration;

/**
 * Security configuration.
 */
@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER)
@EnableConfigurationProperties(RsaKeyProperties.class)
@ComponentScan("com.openclassrooms.mdd.common.configuration")
public class ExtraSecurityConfiguration extends SecurityConfiguration {

    @Autowired
    public ExtraSecurityConfiguration(
            RsaKeyProperties rsaKeys) {
        super(rsaKeys);
    }

    /**
     * Filter chain definition.
     *
     * @param http http security filter chain builder
     * @return a security filter chain
     * @throws Exception A security exception
     */
    @Override
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(ex -> ex.anyExchange().permitAll())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
