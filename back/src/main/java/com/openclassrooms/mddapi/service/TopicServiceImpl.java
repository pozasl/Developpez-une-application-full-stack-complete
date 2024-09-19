package com.openclassrooms.mddapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

import reactor.core.publisher.Flux;

@Service
public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;

    @Autowired
    TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Flux<Topic> findAll() {
        return topicRepository.findAll();
    }
    
}
