package com.openclassrooms.mdd.feeds_api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import com.openclassrooms.mdd.feeds_api.model.FeedPostEntity;

import reactor.test.StepVerifier;

@Tag("S.I.T.")
@DataR2dbcTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class FeedRepositoryTest {
    @Autowired
    private FeedRepository feedRepository;

    private FeedPostEntity feedPost1;
    private FeedPostEntity feedPost2;
    private LocalDateTime date1;
    private LocalDateTime date2;

    @BeforeEach
    void setup() {
        date1 = LocalDateTime.of(2016, 6, 22, 19, 10, 25);
        date2 = LocalDateTime.of(2016, 6, 22, 19, 10, 26);
        feedPost1 = new FeedPostEntity( 2L,"1234567890987654321abcd2", date1);
        feedPost2 = new FeedPostEntity(2L, "1234567890987654321abcd3", date2);
    }

    @Test
    void findByUserId_shoulReturnFeedPosts() {
        feedRepository.findByUserId(2L).as(StepVerifier::create)
            .consumeNextWith(feed -> assertThat(feed).isEqualTo(feedPost1))
            .consumeNextWith(feed -> assertThat(feed).isEqualTo(feedPost2))
            .verifyComplete();
    }

    @Test
    void findByUserIdOrderByCreatedAtDesc_shoulReturnFeedPostsByCreatedAtDesc() {
        feedRepository.findByUserIdOrderByCreatedAtDesc(2L).as(StepVerifier::create)
            .consumeNextWith(feed -> assertThat(feed).isEqualTo(feedPost2))
            .consumeNextWith(feed -> assertThat(feed).isEqualTo(feedPost1))
            .verifyComplete();
    }

    @Test
    void findByUserIdOrderByCreatedAtDesc_shoulReturnFeedPosts() {
        feedRepository.findByUserIdOrderByCreatedAtAsc(2L).as(StepVerifier::create)
            .consumeNextWith(feed -> assertThat(feed).isEqualTo(feedPost1))
            .consumeNextWith(feed -> assertThat(feed).isEqualTo(feedPost2))
            .verifyComplete();
    }

}