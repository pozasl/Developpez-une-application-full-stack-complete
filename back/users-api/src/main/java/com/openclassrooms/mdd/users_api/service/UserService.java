package com.openclassrooms.mdd.users_api.service;

import com.openclassrooms.mdd.users_api.model.UserEntity;
import reactor.core.publisher.Mono;

/**
 * User service
 */
public interface UserService {

    /**
     * Find a user by its id
     *
     * @param id User's id
     * @return User entity
     */
    Mono<UserEntity> findById(Long id);

    /**
     * Update a user by its id
     *
     * @param id User id
     * @param user User entity
     * @return Updated user entity
     */
    Mono<UserEntity> updateUser(Long id, UserEntity user);
}
