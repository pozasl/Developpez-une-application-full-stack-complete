package com.openclassrooms.mdd.topicsapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.topicsapi.model.TopicEntity;
import com.openclassrooms.mdd.topicsapi.repository.TopicRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;

    @Autowired
    TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Flux<TopicEntity> findAll() {
        return topicRepository.findAll();
    }

    @Override
    public Mono<TopicEntity> findByRef(String ref) {
        return topicRepository.findByRef(ref);
    }
    
}
