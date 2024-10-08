package com.openclassrooms.mdd.subscribtions_api.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.api.model.Post;
import com.openclassrooms.mdd.subscribtions_api.model.FeedPostModel;
import com.openclassrooms.mdd.subscribtions_api.repository.SubscribtionRepository;

import reactor.core.publisher.Flux;

@Service
public class ReactiveConsumerService implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(ReactiveConsumerService.class);

    private final ReactiveKafkaConsumerTemplate<String, Post> reactiveKafkaConsumer;
    private final ReactiveProducerService reactiveProducerService;
    private final SubscribtionRepository subRepository;


    ReactiveConsumerService(
            ReactiveKafkaConsumerTemplate<String, Post> reactiveKafkaConsumer,
            ReactiveProducerService reactiveProducerService,
            SubscribtionRepository subRepository) {
        this.reactiveKafkaConsumer = reactiveKafkaConsumer;
        this.reactiveProducerService = reactiveProducerService;
        this.subRepository = subRepository;
    }

    private Flux<Post> consume() {
        log.info("==== Consuming Kafka... ===");
        return reactiveKafkaConsumer
            .receiveAutoAck()
            .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
                    consumerRecord.key(),
                    consumerRecord.value(),
                    consumerRecord.topic(),
                    consumerRecord.offset()))
            .map(ConsumerRecord::value)
            .doOnNext(post -> {
                    log.info("successfully consumed {}={}", Post.class.getSimpleName(), post);
                    subRepository.findByTopicRef(post.getTopic().getRef())
                    .map(sub -> {
                        FeedPostModel feedPost = new FeedPostModel(sub.userId(), post.getId());
                        log.info("Sending post to feed  {}", feedPost.toString());
                        reactiveProducerService.send(feedPost);
                        return sub;
                    })
                    .count()
                    .subscribe(n -> log.info("{} sent !",n));
            })
            .doOnError(
                    throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()));
    }

    @Override
    public void run(String... args) throws Exception {
        consume().subscribe();
    }
}