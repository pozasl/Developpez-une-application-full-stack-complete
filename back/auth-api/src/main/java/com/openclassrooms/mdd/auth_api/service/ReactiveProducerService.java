package com.openclassrooms.mdd.auth_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.api.model.Author;

/**
 * Service to signal username's modification using Kafka.
 */
@Service
public class ReactiveProducerService {

    private final Logger log = LoggerFactory.getLogger(ReactiveProducerService.class);

    private final ReactiveKafkaProducerTemplate<String, Author> reactiveKafkaProducer;

    ReactiveProducerService(ReactiveKafkaProducerTemplate<String, Author> reactiveKafkaProducer) {
        this.reactiveKafkaProducer = reactiveKafkaProducer;
    }

    /**
     * Send an author with its new username
     * @param author
     */
    public void send(Author author) {
        String topicName = "authors";
        log.info("send to topic={}, {}={},", topicName, Author.class.getSimpleName(), author);
        reactiveKafkaProducer.send(topicName, author)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", author, senderResult.recordMetadata().offset()))
                .subscribe();
    }
}