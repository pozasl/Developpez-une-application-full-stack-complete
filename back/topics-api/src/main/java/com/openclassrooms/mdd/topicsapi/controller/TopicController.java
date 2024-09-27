package com.openclassrooms.mdd.topicsapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mdd.api.TopicsApiDelegate;
import com.openclassrooms.mdd.api.model.Topic;
import com.openclassrooms.mdd.topicsapi.mapper.TopicMapper;
import com.openclassrooms.mdd.topicsapi.service.TopicService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TopicController implements TopicsApiDelegate {

    private TopicService topicService;
    private TopicMapper topicMapper;

    @Autowired
    TopicController(TopicService topicService, TopicMapper topicMapper) {
        this.topicService = topicService;
        this.topicMapper = topicMapper;
    }

    @GetMapping("/api/topics")
    @SecurityRequirement(name = "Authorization")
    Flux<Topic> getTopics() {
        return topicMapper.toModel(topicService.findAll());
    }

    @GetMapping("/api/topics/{ref}")
    @SecurityRequirement(name = "Authorization")
    Mono<Topic> getTopicByRef(@PathVariable String ref) {
        return topicMapper.toModel(topicService.findByRef(ref)).switchIfEmpty(Mono.error(new NotFoundException()));
    }

}
