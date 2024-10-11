package com.openclassrooms.mdd.auth_api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
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
@ComponentScan({"com.openclassrooms.mdd.common.configuration", "com.openclassrooms.mdd.common.exception"})
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
                .authorizeExchange(ex -> ex
                .pathMatchers(
                    "/api/auth/login",
                    "/api/auth/register",
                    "/v3/api-docs/**",
                    "/webjars/swagger-ui/**"
                ).permitAll()
                .anyExchange().authenticated())
                .oauth2ResourceServer((oauth2) -> oauth2
				    .jwt(Customizer.withDefaults())
			    )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
