package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.TopicService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @GetMapping("/api/topics/{ref}")
    Mono<Topic> getTopicByRef(@PathVariable String ref) {
        return topicService.findByRef(ref).switchIfEmpty(Mono.error(new NotFoundException()));
    }

}
