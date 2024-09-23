package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Topic;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TopicService {

    Flux<Topic> findAll();

    Mono<Topic> findByRef(String ref);

}
