package com.openclassrooms.mdd.topicsapi.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.topicsapi.model.TopicEntity;

import reactor.core.publisher.Mono;

@Repository
public interface TopicRepository extends ReactiveMongoRepository<TopicEntity, String> {

    Mono<TopicEntity> findByRef(String ref);

}
