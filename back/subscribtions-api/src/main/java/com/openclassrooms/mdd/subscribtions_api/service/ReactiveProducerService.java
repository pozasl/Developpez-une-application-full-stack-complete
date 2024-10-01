package com.openclassrooms.mdd.subscribtions_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.subscribtions_api.model.FeedPostModel;

@Service
public class ReactiveProducerService {

    private final Logger log = LoggerFactory.getLogger(ReactiveProducerService.class);

    private final ReactiveKafkaProducerTemplate<String, FeedPostModel> reactiveKafkaProducer;

    ReactiveProducerService(ReactiveKafkaProducerTemplate<String, FeedPostModel> reactiveKafkaProducer) {
        this.reactiveKafkaProducer = reactiveKafkaProducer;
    }

    public void send(FeedPostModel feedPost) {
        String topicName = "feeds";
        log.info("send to topic={}, {}={},", topicName, FeedPostModel.class.getSimpleName(), feedPost);
        reactiveKafkaProducer.send(topicName, feedPost)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", feedPost, senderResult.recordMetadata().offset()))
                .subscribe();
    }
}