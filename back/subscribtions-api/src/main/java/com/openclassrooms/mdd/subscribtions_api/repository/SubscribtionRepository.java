package com.openclassrooms.mdd.subscribtions_api.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.subscribtions_api.model.SubscribtionEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SubscribtionRepository extends ReactiveMongoRepository<SubscribtionEntity, String>{

    @Query("{ 'topicRef' : ?0 }")
    Flux<SubscribtionEntity> findByTopicRef(String topicRef);

    Flux<SubscribtionEntity> findByUserId(Long userId);

    Mono<SubscribtionEntity> findByUserIdAndTopicRef(long l, String topicRef);
    
}
