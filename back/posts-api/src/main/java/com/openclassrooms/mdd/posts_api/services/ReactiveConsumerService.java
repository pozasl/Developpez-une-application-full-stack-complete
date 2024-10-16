package com.openclassrooms.mdd.posts_api.services;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.api.model.Author;
import com.openclassrooms.mdd.posts_api.repository.AuthorRepository;
import com.openclassrooms.mdd.posts_api.repository.PostRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Reactive Consumer Service for author's topic
 */
@Service
public class ReactiveConsumerService implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(ReactiveConsumerService.class);

    private final ReactiveKafkaConsumerTemplate<String, Author> reactiveKafkaConsumer;
    private final AuthorRepository authorRepository;
    private final PostRepository postRepository; 


    ReactiveConsumerService(
            ReactiveKafkaConsumerTemplate<String, Author> reactiveKafkaConsumer,
            AuthorRepository authorRepository,
            PostRepository postRepository
        ) {
        this.reactiveKafkaConsumer = reactiveKafkaConsumer;
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
    }

    /**
     * Consume the author topic and update Author's username in author, posts and replies
     *
     * @return Authors
     */
    private Flux<Author> consume() {
        log.info("==== Consuming Kafka... ===");
        return reactiveKafkaConsumer
            .receiveAutoAck()
            .map(ConsumerRecord::value)
            .doOnNext(author -> {
                    log.info("successfully consumed {}={}", Author.class.getSimpleName(), author.getUserId());
                    authorRepository.findAndUpdateUserNameByUserId(author.getUserId(), author.getUserName())
                        .switchIfEmpty(Mono.just(0L))
                        .subscribe((userId) -> log.info("Author's username updated for {}", userId));
                    postRepository.findAndUpdatePostAuthorUserNameByAuthorUserId(author.getUserId(), author.getUserName())
                        .switchIfEmpty(Mono.just(0L))
                        .subscribe((userId) -> log.info("Posts's author username updated for {}", userId));
                    authorRepository.findByUserId(author.getUserId())
                    .map(a -> {
                        a.replies().forEach(postId -> {
                            postRepository.findAndUpdatePostRepliesAuthorUserNameByPostId(postId, author.getUserId(), author.getUserName())
                            .subscribe(r -> log.info("Posts's author username updated for {}", r.getModifiedCount()));
                        });
                        return a;
                    })
                    .subscribe((updateAuthor) -> log.info("Finished  all username updated for {} {} {}", updateAuthor.userName(), updateAuthor.userId(), updateAuthor.id()));
                })
            .doOnError(
                    throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()));
    }

    /*
     * Launch consumer at runtime
     */
    @Override
    public void run(String... args) throws Exception {
        consume().subscribe();
    }
}