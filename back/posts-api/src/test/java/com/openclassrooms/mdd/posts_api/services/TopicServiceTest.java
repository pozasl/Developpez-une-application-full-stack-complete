package com.openclassrooms.mdd.posts_api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.mdd.posts_api.model.TopicEntity;
import com.openclassrooms.mdd.posts_api.repository.TopicRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicServiceImpl topicService;

    @Test
    void findAll_shoulReturnTopicsFlux() {
        TopicEntity topic1 = new TopicEntity("java", "Java", "Java bla bla bla");
        TopicEntity topic2 = new TopicEntity("angular", "Angular", "Angular bla bla bla");
        when(topicRepository.findAll()).thenReturn(Flux.just(topic1, topic2));
        topicService.findAll().as(StepVerifier::create)
        .consumeNextWith(t1 -> {
            assertThat(t1).isEqualTo(topic1);
        })
        .consumeNextWith(t2 -> {
            assertThat(t2).isEqualTo(topic2);
        })
        .verifyComplete();
    }

    @Test
    void findByRef_shoulReturnTopicMono() {
        TopicEntity topic = new TopicEntity("java", "Java", "Java bla bla bla");
        when(topicRepository.findByRef("java")).thenReturn(Mono.just(topic));
        topicService.findByRef("java").as(StepVerifier::create)
                .consumeNextWith(t -> {
                    assertThat(t).isEqualTo(topic);
                    verify(topicRepository).findByRef("java");
                })
                .verifyComplete();
    }

}
