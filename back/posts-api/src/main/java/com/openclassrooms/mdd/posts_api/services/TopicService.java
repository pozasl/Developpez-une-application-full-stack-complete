package com.openclassrooms.mdd.posts_api.services;

import com.openclassrooms.mdd.posts_api.model.TopicEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TopicService {

    Flux<TopicEntity> findAll();

    Mono<TopicEntity> findByRef(String ref);

}
