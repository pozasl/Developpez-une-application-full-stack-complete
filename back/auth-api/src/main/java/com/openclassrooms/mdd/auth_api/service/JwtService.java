package com.openclassrooms.mdd.auth_api.service;

import org.springframework.security.core.Authentication;

import com.openclassrooms.mdd.auth_api.model.UserDetailEntity;

/**
 * Service managing JWT token
 */
public interface JwtService {

    /**
     * Generate a token from authentication's informations
     *
     * @param auth the authentication informations
     * @return The token
     */
    String generateToken(Authentication auth);

    /**
     * Generate a token from a UserDetail entity
     *
     * @param userDetails the user
     * @return the token
     */
    public String generateTokenFromUserdetail(UserDetailEntity userDetails);

}