package com.openclassrooms.mdd.topicsapi.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.topicsapi.model.Topic;

import reactor.core.publisher.Mono;

@Repository
public interface TopicRepository extends ReactiveMongoRepository<Topic, String> {

    Mono<Topic> findByRef(String ref);

}
