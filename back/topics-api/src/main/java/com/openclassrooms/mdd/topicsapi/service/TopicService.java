package com.openclassrooms.mdd.topicsapi.service;

import com.openclassrooms.mdd.topicsapi.model.Topic;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TopicService {

    Flux<Topic> findAll();

    Mono<Topic> findByRef(String ref);

}
