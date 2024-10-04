package com.openclassrooms.mdd.users_api.mapper;

import com.openclassrooms.mdd.api.model.NewUser;
import com.openclassrooms.mdd.api.model.User;
import com.openclassrooms.mdd.users_api.model.UserEntity;

import reactor.core.publisher.Mono;

public interface UserMapper {

    User toModel(UserEntity userEntity);

    Mono<User> toModel(Mono<UserEntity> userEntity);

    UserEntity toEntity(NewUser newUser);
    
}
