package com.openclassrooms.mdd.users_api.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.users_api.model.UserEntity;

import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends R2dbcRepository<UserEntity, Long>{

    Flux<UserEntity> findByEmail(String email);
    
}
