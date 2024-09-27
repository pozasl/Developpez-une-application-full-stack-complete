package com.openclassrooms.mdd.topicsapi.service;

import com.openclassrooms.mdd.topicsapi.model.TopicEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TopicService {

    Flux<TopicEntity> findAll();

    Mono<TopicEntity> findByRef(String ref);

}
