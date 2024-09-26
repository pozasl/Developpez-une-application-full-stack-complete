package com.openclassrooms.mdd.auth_api.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.auth_api.model.UserDetailEntity;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<UserDetailEntity, Long>{
    
    Mono<UserDetailEntity> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);

}
