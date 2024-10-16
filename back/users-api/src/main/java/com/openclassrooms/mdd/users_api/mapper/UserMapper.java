package com.openclassrooms.mdd.users_api.mapper;

import com.openclassrooms.mdd.api.model.NewUser;
import com.openclassrooms.mdd.api.model.User;
import com.openclassrooms.mdd.users_api.model.UserEntity;

import reactor.core.publisher.Mono;

/**
 * User mapper
 */
public interface UserMapper {

    /**
     * Convert entity to model
     *
     * @param userEntity User entity
     * @return User model
     */
    User toModel(UserEntity userEntity);

    /**
     * Convert entity to model reactively
     *
     * @param userEntity
     * @return
     */
    Mono<User> toModel(Mono<UserEntity> userEntity);

    /**
     * Convert NewUser model to User entity
     *
     * @param newUser NewUser model
     * @return User entity
     */
    UserEntity toEntity(NewUser newUser);
    
}
