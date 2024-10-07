package com.openclassrooms.mdd.posts_api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.TestPropertySource;

import com.openclassrooms.mdd.posts_api.model.AuthorEntity;

import reactor.test.StepVerifier;

@DataMongoTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ReactiveMongoTemplate template;

    private  AuthorEntity bob;

    @BeforeEach
    void setup() {
        bob = new AuthorEntity("123456789098765432100001", 1L, "Bob", List.of(), List.of());
        authorRepository.save(bob).block();
    }

    @AfterEach
    void teardown() {
        template.dropCollection("authors").block();
    }

    @Test
    void testFindByUserId() {
        authorRepository.findByUserId(1L).as(StepVerifier::create)
        .consumeNextWith(author -> assertThat(author).isEqualTo(bob))
        .verifyComplete();
    }
}
