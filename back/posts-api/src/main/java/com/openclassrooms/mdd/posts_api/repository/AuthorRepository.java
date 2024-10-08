package com.openclassrooms.mdd.posts_api.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.posts_api.model.AuthorEntity;

import reactor.core.publisher.Mono;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<AuthorEntity, String>{
    @Query("{ 'userId' : ?0 }")
    Mono<AuthorEntity> findByUserId(Long userId);
}
