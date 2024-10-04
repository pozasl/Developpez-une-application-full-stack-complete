package com.openclassrooms.mdd.users_api.service;

import com.openclassrooms.mdd.users_api.model.UserEntity;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserEntity> findById(Long id);

    Mono<UserEntity> updateUser(Long id, UserEntity user);
}
