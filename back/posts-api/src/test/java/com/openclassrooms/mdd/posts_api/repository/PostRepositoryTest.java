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

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ReactiveMongoTemplate template;

    private List<PostEntity> posts;
    private List<AuthorEntity> authors;

    AuthorEntity bob;
    AuthorEntity alice;

    @BeforeEach
    void setup() {
        Date date = new Date(0L);
        bob = new AuthorEntity("123456789098765432100001", 1L, "Bob", List.of(), List.of());
        alice = new AuthorEntity("123456789098765432100002", 2L, "Alice", List.of(), List.of());
        authors = authorRepository.saveAll(List.of(bob, alice)).collectList().block();
        ReplyEntity reply2 = new ReplyEntity("Nice one !", new Date(1000L), authors.get(0));
        PostEntity post1 = new PostEntity(null,"Java in a Nutshell", "Java Bla bla bla", date, authors.get(0), "java", List.of());
        PostEntity post2 = new PostEntity(null,"Java in a Nutshell", "Java Bla bla bla", date, authors.get(1), "angular",  List.of(reply2));
        postRepository.saveAll(List.of(post1,post2)).collectList().block();
        posts = postRepository.findAll().collectList().block();
    }

    @AfterEach
    void teardown() {
        template.dropCollection("posts").block();
        template.dropCollection("authors").block();
    }

    @Test
    void testFindById() {
        Mono<PostEntity> postMono = postRepository.findById(posts.get(0).id());
        postMono.as(StepVerifier::create)
        .consumeNextWith(post -> assertThat(post).isEqualTo(posts.get(0)))
        .verifyComplete();
    }

    @Test
    void addReplyToPostId() {
        ReplyEntity reply1 = new ReplyEntity("Trop cool", new Date(1000L), alice);
        postRepository.addReplyToPostId(posts.get(0).id(), reply1)
            .then(postRepository.findById(posts.get(0).id()))
        .as(StepVerifier::create)
        .consumeNextWith(post -> {
            assertThat(post.replies()).hasSize(1);
            assertThat(post.replies().get(0)).isEqualTo(reply1);
        })
        .verifyComplete();
    }
}
