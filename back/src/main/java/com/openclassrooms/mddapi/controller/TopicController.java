package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.TopicService;

import reactor.core.publisher.Flux;

@RestController
public class TopicController {

    private TopicService topicService;

    @Autowired
    TopicController (TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/api/topics")
    Flux<Topic> getTopics() {
        return topicService.findAll();
    }

}
