package com.openclassrooms.mdd.subscribtions_api.service;

import com.openclassrooms.mdd.subscribtions_api.model.SubscribtionEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Subscribtion service
 */
public interface SubscribtionService {

    /**
     * Subscribe a user to a topic
     *
     * @param userId The user's id
     * @param topicRef The topic's ref
     * @return A Subscribtion entity
     */
    Mono<SubscribtionEntity>subscribeUserToTopic(Long userId, String topicRef);

    /**
     * Unsubscribe a user to a topic
     *
     * @param userId The user's id
     * @param topicRef The topic's ref
     */
    Mono<Void>unsubscribeUserToTopic(Long userId, String topicRef);

    /**
     * Find subscribtions by user id
     *
     * @param userId The user id
     * @return Subscribtion entities
     */
    Flux<SubscribtionEntity> findSubsByUserId(Long userId);

    /**
     * Find subscribtions by topic ref
     *
     * @param topicRef The topic's ref
     * @return Subscribtion entities
     */
    Flux<SubscribtionEntity> findSubsByTopicRef(String topicRef);
}
