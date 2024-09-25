package com.openclassrooms.mdd.auth_api.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.openclassrooms.mdd.auth_api.model.UserEntity;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long>{
    
}
