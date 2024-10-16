package com.openclassrooms.mdd.auth_api.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.auth_api.model.UserDetailEntity;

import reactor.core.publisher.Mono;

/**
 * Reactive repository for UserDetail entity
 */
@Repository
public interface UserRepository extends R2dbcRepository<UserDetailEntity, Long>{
    
    /**
     * Find a user by email
     *
     * @param email User's email
     * @return User detail entity
     */
    Mono<UserDetailEntity> findByEmail(String email);

    /**
     * Check if a user exist with the provided email
     *
     * @param email the user's email
     * @return if the the user exists
     */
    Mono<Boolean> existsByEmail(String email);

}