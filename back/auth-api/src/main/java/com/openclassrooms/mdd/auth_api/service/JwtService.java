package com.openclassrooms.mdd.auth_api.service;

import org.springframework.security.core.Authentication;

public interface JwtService {

    String generateToken(Authentication auth);

    // String extractUsername(String token);

    // boolean validate(String token, String username);
}
