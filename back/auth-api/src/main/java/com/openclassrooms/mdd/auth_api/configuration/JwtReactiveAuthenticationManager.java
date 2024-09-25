package com.openclassrooms.mdd.auth_api.configuration;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;

import com.openclassrooms.mdd.auth_api.service.JwtService;

import reactor.core.publisher.Mono;

public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager{

    private JwtService jwtService;

    JwtReactiveAuthenticationManager(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
            .cast(JwtToken.class)
            .filter(jwtToken -> jwtService.isTokenValid(jwtToken.getToken()))
            .map(jwtToken -> jwtToken.withAuthenticated(true))
            .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid token.")));
    }
    
}
