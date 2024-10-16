package com.openclassrooms.mdd.feeds_api.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.feeds_api.model.FeedPostEntity;
import com.openclassrooms.mdd.feeds_api.model.FeedPostModel;
import com.openclassrooms.mdd.feeds_api.repository.FeedRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
/**
 * Reactive consumer service to update post in feeds from Kafka
 */
public class ReactiveConsumerService implements CommandLineRunner{

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

    /**
     * Consume the feeds topic in Kafka and insert post in feeds
     *
     * @return Feeds' posts
     */
    private Flux<FeedPostModel> consume() {
        log.info("=== Consuming Kafka ====");
        return reactiveKafkaConsumer
            .receiveAutoAck()
            .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
            )
            .map(ConsumerRecord::value)
            .doOnNext(feedPost -> {
                log.info("successfully consumed {}=>{}", feedPost.userId(), feedPost.postRef());
                FeedPostEntity entity = new FeedPostEntity();
                entity.setUserId(feedPost.userId());
                entity.setPostRef(feedPost.postRef());
                entity.setCreatedAt(feedPost.createdAt().toLocalDateTime());
                feedRepository.save(entity).subscribe((fp -> log.info("saved feedPost {}", fp)));
            })
            .doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()))
        ;
    }

    /**
     * Launch feeds consumption at runtime
     */
    @Override
    public void run(String... args) throws Exception {
        consume().subscribe();
    }

}
