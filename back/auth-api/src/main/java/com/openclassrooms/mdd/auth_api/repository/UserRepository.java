package com.openclassrooms.mdd.auth_api.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.auth_api.model.UserEntity;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long>{
    
    Mono<UserEntity> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);

}
