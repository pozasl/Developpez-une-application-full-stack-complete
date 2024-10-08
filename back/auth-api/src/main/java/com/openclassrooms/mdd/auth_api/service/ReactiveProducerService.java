package com.openclassrooms.mdd.auth_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.api.model.Author;

@Service
public class ReactiveProducerService {

    private final Logger log = LoggerFactory.getLogger(ReactiveProducerService.class);

    private final ReactiveKafkaProducerTemplate<String, Author> reactiveKafkaProducer;

    ReactiveProducerService(ReactiveKafkaProducerTemplate<String, Author> reactiveKafkaProducer) {
        this.reactiveKafkaProducer = reactiveKafkaProducer;
    }

    public void send(Author feedPost) {
        String topicName = "authors";
        log.info("send to topic={}, {}={},", topicName, Author.class.getSimpleName(), feedPost);
        reactiveKafkaProducer.send(topicName, feedPost)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", feedPost, senderResult.recordMetadata().offset()))
                .subscribe();
    }
}