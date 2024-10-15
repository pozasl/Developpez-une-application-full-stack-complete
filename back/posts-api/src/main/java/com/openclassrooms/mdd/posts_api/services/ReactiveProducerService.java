package com.openclassrooms.mdd.posts_api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.api.model.Post;

/**
 * Reactive Producer Service for posts topic
 */
@Service
public class ReactiveProducerService {

    
    private String postTopic;

    private final Logger log = LoggerFactory.getLogger(ReactiveProducerService.class);

    private final ReactiveKafkaProducerTemplate<String, Post> reactiveKafkaProducer;

    ReactiveProducerService(
        ReactiveKafkaProducerTemplate<String, Post> reactiveKafkaProducer,
        @Value(value = "${topic.posts.name}")String postTopic
    ) {
        this.reactiveKafkaProducer = reactiveKafkaProducer;
        this.postTopic = postTopic;
    }

    /**
     * Send the post in the kafka topic
     *
     * @param post the post to send
     */
    public void send(Post post) {
        log.info("send to topic={}, {}={},", postTopic, Post.class.getSimpleName(), post);
        reactiveKafkaProducer.send(postTopic, post)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", post, senderResult.recordMetadata().offset()))
                .doOnError(e -> log.info("error {} ",e.getMessage()))
                .subscribe();
    }
}