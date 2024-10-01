package com.openclassrooms.mdd.feeds_api.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.feeds_api.model.FeedPostModel;
import com.openclassrooms.mdd.feeds_api.repository.FeedRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class ReactiveConsumerService {

    private final ReactiveKafkaConsumerTemplate<String, FeedPostModel> reactiveKafkaConsumer;
    private FeedRepository feedRepository;

    @Autowired
    ReactiveConsumerService(
        ReactiveKafkaConsumerTemplate<String, FeedPostModel> reactiveKafkaConsumer,
        FeedRepository feedRepository
        ) {
        this.reactiveKafkaConsumer = reactiveKafkaConsumer;
        this.feedRepository = feedRepository;
    }

    private Flux<FeedPostModel> consume() {
        return reactiveKafkaConsumer
            .receiveAutoAck()
            .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
            )
            .map(ConsumerRecord::value)
            .doOnNext(feedPost -> log.info("successfully consumed {}=>{}", feedPost.userId(), feedPost.postRef()))
                .doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()))
                .publish();
    }

}
