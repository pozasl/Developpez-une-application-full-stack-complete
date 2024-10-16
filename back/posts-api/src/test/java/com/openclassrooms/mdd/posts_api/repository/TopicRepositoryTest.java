package com.openclassrooms.mdd.posts_api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.TestPropertySource;

import com.openclassrooms.mdd.posts_api.model.TopicEntity;

@DataMongoTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TopicRepositoryTest {

    @Autowired
    ReactiveMongoOperations operations;

    @Autowired
    TopicRepository repository;

    @Test
    void findAll_shouldReturnTopics() {
        List<TopicEntity> topics = repository.findAll().collectList().block();
        assertThat(topics).hasSize(6);
    }

    @Test
    void withExistingRef_findByRef_shouldReturnTopic() {
        TopicEntity topic = repository.findByRef("java").block();
        assertThat(topic.name()).isEqualTo("Java");
    }
    
}
