package com.openclassrooms.mdd.posts_api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.api.model.Post;

@Service
public class ReactiveProducerService {

    private final Logger log = LoggerFactory.getLogger(ReactiveProducerService.class);

    private final ReactiveKafkaProducerTemplate<String, Post> reactiveKafkaProducer;

    ReactiveProducerService(ReactiveKafkaProducerTemplate<String, Post> reactiveKafkaProducer) {
        this.reactiveKafkaProducer = reactiveKafkaProducer;
    }

    public void send(Post post) {
        // TODO: Use post's topicRef param instead
        String topicName = "posts";
        log.info("send to topic={}, {}={},", topicName, Post.class.getSimpleName(), post);
        reactiveKafkaProducer.send(topicName, post)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", post, senderResult.recordMetadata().offset()))
                .doOnError(e -> log.info("error {} ",e.getMessage()))
                .subscribe();
    }
}