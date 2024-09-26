package com.openclassrooms.mdd.users_api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

/**
 * Security configuration.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    private final RsaKeyProperties rsaKeys;

    @Autowired
    public SecurityConfiguration(
            RsaKeyProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
    }

    /**
     * Filter chain definition.
     *
     * @param http http security filter chain builder
     * @return a security filter chain
     * @throws Exception A security exception
     */
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(ex -> ex
                .pathMatchers(
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

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwKeys = new RSAKey.Builder(rsaKeys.publicKey())
                .privateKey(rsaKeys.privateKey())
                .build();
        JWKSource<SecurityContext> jwKeysSrc = new ImmutableJWKSet<>(new JWKSet(jwKeys));
        return new NimbusJwtEncoder(jwKeysSrc);
    }
}
