package com.openclassrooms.mdd.auth_api.service;

import org.springframework.security.core.Authentication;

import com.openclassrooms.mdd.auth_api.model.UserDetailEntity;

public interface JwtService {

    String generateToken(Authentication auth);

    public String generateTokenFromUserdetail(UserDetailEntity userDetails);

    // String extractUsername(String token);

    // boolean validate(String token, String username);
}
