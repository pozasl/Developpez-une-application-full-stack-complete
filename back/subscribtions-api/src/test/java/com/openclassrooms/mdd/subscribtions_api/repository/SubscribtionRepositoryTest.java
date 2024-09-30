package com.openclassrooms.mdd.subscribtions_api.repository;

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

import com.openclassrooms.mdd.subscribtions_api.model.SubscribtionEntity;

import reactor.test.StepVerifier;

@DataMongoTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class SubscribtionRepositoryTest {

    @Autowired
    private SubscribtionRepository subscriptionRepository;

    @Autowired
    private ReactiveMongoTemplate template;

    private Date date;
    private List<SubscribtionEntity> subs;


    @BeforeEach
    void setup() {
        date = new Date(0L);
        
        SubscribtionEntity sub1 = new SubscribtionEntity(null,1L, "java", date);
        SubscribtionEntity sub2 = new SubscribtionEntity(null,1L, "angular", date);
        SubscribtionEntity sub3 = new SubscribtionEntity(null,2L, "angular", date);
        subs = subscriptionRepository.saveAll(List.of(sub1, sub2, sub3)).collectList().block();
    }

    @AfterEach
    void tearDown() {
        template.dropCollection("subscribtions").block();
    }

    @Test
    void findByTopicRef_shouldReturnSubs() {
        subscriptionRepository.findByTopicRef("angular").as(StepVerifier::create)
            .consumeNextWith(sub -> assertThat(sub).isEqualTo(subs.get(1)))
            .consumeNextWith(sub -> assertThat(sub).isEqualTo(subs.get(2)))
            .verifyComplete();
    }

    @Test
    void findByUserId_shouldReturnSubs() {
        subscriptionRepository.findByUserId(1L).as(StepVerifier::create)
            .consumeNextWith(sub -> assertThat(sub).isEqualTo(subs.get(0)))
            .consumeNextWith(sub -> assertThat(sub).isEqualTo(subs.get(1)))
            .verifyComplete();
    }


    @Test
    void findByUserIdAndTopicRef_shouldReturnSub() {
        subscriptionRepository.findByUserIdAndTopicRef(1L,"angular").as(StepVerifier::create)
            .consumeNextWith(sub -> assertThat(sub).isEqualTo(subs.get(1)))
            .verifyComplete();
    }

}
