package com.openclassrooms.mdd.posts_api.services;

import com.openclassrooms.mdd.posts_api.model.TopicEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Topic service
 */
public interface TopicService {

    /**
     * Find all topics
     *
     * @return topics
     */
    Flux<TopicEntity> findAll();

    /**
     * Find a topic by its ref
     *
     * @param ref the topic's red
     * @return The topic
     */
    Mono<TopicEntity> findByRef(String ref);

}
