package com.openclassrooms.mdd.users_api.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.users_api.model.UserEntity;

import reactor.core.publisher.Flux;

/**
 * User repository
 */
@Repository
public interface UserRepository extends R2dbcRepository<UserEntity, Long>{

    /**
     *  Find a user by its email
     *
     * @param email User's email
     * @return User entity
     */
    Flux<UserEntity> findByEmail(String email);
    
}
