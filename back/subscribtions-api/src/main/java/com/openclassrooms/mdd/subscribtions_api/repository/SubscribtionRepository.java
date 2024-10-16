package com.openclassrooms.mdd.subscribtions_api.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.subscribtions_api.model.SubscribtionEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Subscribtion repository
 */
@Repository
public interface SubscribtionRepository extends ReactiveMongoRepository<SubscribtionEntity, String>{

    /**
     * Find users subscribtions by topic's ref
     *
     * @param topicRef The topic's ref
     * @return The subscribtions
     */
    @Query("{ 'topicRef' : ?0 }")
    Flux<SubscribtionEntity> findByTopicRef(String topicRef);

    /**
     * Find topics subscribtions by subscribed user id
     *
     * @param userId The user id
     * @return The subscribtions
     */
    Flux<SubscribtionEntity> findByUserId(Long userId);

    /**
     * Find a subscribtion by user id and topic ref
     *
     * @param userId The user id
     * @param topicRef The topic
     * @return The subscribtion
     */
    Mono<SubscribtionEntity> findByUserIdAndTopicRef(long userId, String topicRef);
    
}
