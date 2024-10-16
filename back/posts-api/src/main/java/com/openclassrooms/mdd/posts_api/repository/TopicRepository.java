package com.openclassrooms.mdd.posts_api.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.posts_api.model.TopicEntity;

import reactor.core.publisher.Mono;

/**
 * Topic repository
 */
@Repository
public interface TopicRepository extends ReactiveMongoRepository<TopicEntity, String> {

    /**
     * Find topic by its reference
     *
     * @param ref the topic's reference
     * @return The topic
     */
    Mono<TopicEntity> findByRef(String ref);

}
