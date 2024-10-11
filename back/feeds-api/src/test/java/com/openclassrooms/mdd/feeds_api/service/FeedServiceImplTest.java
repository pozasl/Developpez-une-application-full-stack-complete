package com.openclassrooms.mdd.feeds_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.mdd.feeds_api.model.FeedPostEntity;
import com.openclassrooms.mdd.feeds_api.repository.FeedRepository;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class FeedServiceImplTest {

    @Mock
    FeedRepository feedRepository;

    @InjectMocks
    FeedServiceImpl feedService;

    private LocalDateTime date1;
    private LocalDateTime date2;

    private FeedPostEntity feed1;
    private FeedPostEntity feed2;

    @BeforeEach
    void setup() {
        date1 = LocalDateTime.now().minusDays(1);
        date2 = LocalDateTime.now();
        feed1 = new FeedPostEntity(1L, "1234567890987654321abcd0", date1);
        feed2 = new FeedPostEntity(1L, "1234567890987654321abcd1", date2);
    }

    @Test
    void testFindPostByUserId() {
        when(feedRepository.findByUserId(1L)).thenReturn(Flux.just(feed1, feed2));
        feedService.findPostByUserId(1L, "none").as(StepVerifier::create)
            .consumeNextWith(feed -> assertThat(feed).isEqualTo(feed1))
            .consumeNextWith(feed -> assertThat(feed).isEqualTo(feed2))
            .verifyComplete();
    }

    @Test
    void testFindPostByUserIdAsc() {
        when(feedRepository.findByUserIdOrderByCreatedAtAsc(1L)).thenReturn(Flux.just(feed1, feed2));
        feedService.findPostByUserId(1L, "asc").as(StepVerifier::create)
            .consumeNextWith(feed -> assertThat(feed).isEqualTo(feed1))
            .consumeNextWith(feed -> assertThat(feed).isEqualTo(feed2))
            .verifyComplete();
    }

    @Test
    void testFindPostByUserIdDesc() {
        when(feedRepository.findByUserIdOrderByCreatedAtDesc(1L)).thenReturn(Flux.just(feed2, feed1));
        feedService.findPostByUserId(1L, "desc").as(StepVerifier::create)
            .consumeNextWith(feed -> assertThat(feed).isEqualTo(feed2))
            .consumeNextWith(feed -> assertThat(feed).isEqualTo(feed1))
            .verifyComplete();
    }
}
