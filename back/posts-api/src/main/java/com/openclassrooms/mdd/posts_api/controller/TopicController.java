package com.openclassrooms.mdd.posts_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mdd.api.TopicsApiDelegate;
import com.openclassrooms.mdd.api.model.Topic;
import com.openclassrooms.mdd.common.exception.ResourceNotFoundException;
import com.openclassrooms.mdd.posts_api.mapper.TopicMapper;
import com.openclassrooms.mdd.posts_api.services.TopicService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Topics API controller
 */
@RestController
@SecurityRequirement(name = "Authorization")
public class TopicController implements TopicsApiDelegate {

    private TopicService topicService;
    private TopicMapper topicMapper;

    @Autowired
    TopicController(TopicService topicService, TopicMapper topicMapper) {
        this.topicService = topicService;
        this.topicMapper = topicMapper;
    }

    /**
     * Get All Topics
     *
     * @return A list of Topics
     */
    @GetMapping("/api/topics")
    Flux<Topic> getTopics() {
        return topicMapper.toModel(topicService.findAll());
    }

    /**
     * Get a topic by its ref
     *
     * @param ref the topic's ref
     * @return The topic
     */
    @GetMapping("/api/topics/{ref}")
    Mono<Topic> getTopicByRef(@PathVariable String ref) {
        return topicMapper.toModel(topicService.findByRef(ref)).switchIfEmpty(Mono.error(new ResourceNotFoundException()));
    }

}
