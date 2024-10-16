package com.openclassrooms.mdd.auth_api.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;

import com.openclassrooms.mdd.api.model.NewUser;
import com.openclassrooms.mdd.auth_api.model.UserDetailEntity;

import reactor.core.publisher.Mono;


/**
 * Service to get/update User'data
 */
public interface UserService extends ReactiveUserDetailsService{

        /**
         * Create a new user
         *
         * @param newUser new User DTO
         * @return created User detail entity
         */
        Mono<UserDetailEntity> createUser(NewUser newUser);

        /**
         * Check if a user with provided email exists
         *
         * @param email user's email
         * @return If the user exists
         */
        Mono<Boolean> existsByEmail(String email);

        /**
         * Update a User and encode its password if changed
         *
         * @param user User to update
         * @param encodePass does the password need to be encrypted
         * @return The updated user
         */
        Mono<UserDetailEntity> updateUser(UserDetailEntity user, Boolean encodePass);

        /**
         * Find a user by its email
         * @param email
         * @return
         */
        Mono<UserDetailEntity> findByEmail(String email);
}
