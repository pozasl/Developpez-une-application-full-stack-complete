package com.openclassrooms.mdd.posts_api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.TestPropertySource;

import com.openclassrooms.mdd.posts_api.model.AuthorEntity;
import com.openclassrooms.mdd.posts_api.model.PostEntity;
import com.openclassrooms.mdd.posts_api.model.ReplyEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReactiveMongoTemplate template;

    private List<PostEntity> posts;

    AuthorEntity bob;
    AuthorEntity alice;

    @BeforeEach
    void setup() {
        Date date = new Date(0L);
        bob = new AuthorEntity(1L, "Bob");
        alice = new AuthorEntity(2L, "Alice");
        PostEntity post1 = new PostEntity(null,"Java in a Nutshell", "Java Bla bla bla", date, bob, "java", List.of());
        PostEntity post2 = new PostEntity(null,"Java in a Nutshell", "Java Bla bla bla", date, alice, "angular",  List.of());
        postRepository.saveAll(List.of(post1,post2)).collectList().block();
        posts = postRepository.findAll().collectList().block();
    }

    @AfterEach
    void teardown() {
        template.dropCollection("posts").block();
    }

    @Test
    void testFindById() {
        Mono<PostEntity> postMono = postRepository.findById(posts.get(0).id());
        postMono.as(StepVerifier::create)
        .consumeNextWith(post -> assertThat(post).isEqualTo(posts.get(0)))
        .verifyComplete();
    }

    @Test
    void testFindByAuthorUserId() {
        Flux<PostEntity> postsFlux = postRepository.findByAuthorUserId(2L);
        postsFlux.as(StepVerifier::create)
        .consumeNextWith(post -> assertThat(post).isEqualTo(posts.get(1)))
        .verifyComplete();
    }

    @Test
    void testUpdatePostAuthorByAuthorUserId() {
        Mono<Void> mono = postRepository.updatePostAuthorByAuthorUserId(1L, "Boby");
        mono.block();
        postRepository.findById(posts.get(0).id()).as(StepVerifier::create)
        .consumeNextWith(post -> assertThat(post.author().userName()).isEqualTo("Boby"))
        .verifyComplete();
    }

    @Test
    void addReplyToPostId() {
        ReplyEntity reply1 = new ReplyEntity("Trop cool", new Date(1000L), alice);
        Mono<Void> mono = postRepository.addReplyToPostId(posts.get(0), reply1);
        mono.block();
        postRepository.findById(posts.get(0).id()).as(StepVerifier::create)
        .consumeNextWith(post -> assertThat(post.replies()).hasSize(1))
        .verifyComplete();
    }
}
