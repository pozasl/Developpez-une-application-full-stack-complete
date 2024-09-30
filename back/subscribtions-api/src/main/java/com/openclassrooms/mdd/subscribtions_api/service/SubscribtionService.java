package com.openclassrooms.mdd.subscribtions_api.service;

import com.openclassrooms.mdd.subscribtions_api.model.SubscribtionEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubscribtionService {

    Mono<SubscribtionEntity>subscribeUserToTopic(Long userId, String topicRef);

    Mono<Void>unsubscribeUserToTopic(Long userId, String topicRef);

    Flux<SubscribtionEntity> findSubsByUserId(Long userId);

    Flux<SubscribtionEntity> findSubsByTopicRef(String topicRef);
}
