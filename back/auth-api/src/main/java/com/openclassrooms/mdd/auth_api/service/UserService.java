package com.openclassrooms.mdd.auth_api.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;

import com.openclassrooms.mdd.api.model.NewUser;
import com.openclassrooms.mdd.auth_api.model.UserDetailEntity;

import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsService{

        Mono<UserDetailEntity> createUser(NewUser newUser);

        Mono<Boolean> existsByEmail(String email); 
}
