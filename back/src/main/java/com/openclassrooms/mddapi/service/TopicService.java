package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Topic;

import reactor.core.publisher.Flux;

public interface TopicService {

    Flux<Topic> findAll();

}
