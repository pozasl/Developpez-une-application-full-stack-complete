package com.openclassrooms.mdd.subscribtions_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.mdd.subscribtions_api.model.SubscribtionEntity;
import com.openclassrooms.mdd.subscribtions_api.repository.SubscribtionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class SubscribtionServiceImplTest {

    @Mock
    private SubscribtionRepository subRepository;

    @InjectMocks
    private SubscribtionServiceImpl subscribtionServiceImpl;

    private Date date;

    SubscribtionEntity sub1;
    SubscribtionEntity sub2;
    SubscribtionEntity sub3;

    @BeforeEach
    void setup() {
        date = new Date(0L);
        
        sub1 = new SubscribtionEntity("12345678909876543210abcdec",1L, "java", date);
        sub2 = new SubscribtionEntity("12345678909876543210abcded", 1L, "angular", date);
        sub3 = new SubscribtionEntity("12345678909876543210abcdee", 2L, "angular", date);

    }

    @Test
    void findSubsByTopicRef_shouldReturnSubsFlux() {
        when(subRepository.findByTopicRef("angular")).thenReturn(Flux.just(sub2,sub3));
        subscribtionServiceImpl.findSubsByTopicRef("angular").as(StepVerifier::create)
            .consumeNextWith(sub -> assertThat(sub).isEqualTo(sub2))
            .consumeNextWith(sub -> assertThat(sub).isEqualTo(sub3))
            .verifyComplete();
    }

    @Test
    void findSubsByUserId_shouldReturnSubsFlux() {
        when(subRepository.findByUserId(1L)).thenReturn(Flux.just(sub1,sub2));
        subscribtionServiceImpl.findSubsByUserId(1L).as(StepVerifier::create)
            .consumeNextWith(sub -> assertThat(sub).isEqualTo(sub1))
            .consumeNextWith(sub -> assertThat(sub).isEqualTo(sub2))
            .verifyComplete();
    }

    @Test
    void subscribeUserToTopic_shouldCreateSubAndReturnItsMono() {
        SubscribtionEntity newSub = new SubscribtionEntity("12345678909876543210abcdef", 2L, "java", date);
        when(subRepository.findByUserIdAndTopicRef(2L, "java")).thenReturn(Mono.empty());
        when(subRepository.save(any())).thenReturn(Mono.just(newSub));
        subscribtionServiceImpl.subscribeUserToTopic(2L, "java").as(StepVerifier::create)
            .consumeNextWith(sub -> assertThat(sub).isEqualTo(newSub))
            .verifyComplete();
    }

    @Test
    void unsubscribeUserToTopic_shouldDeleteSubAndReturnMonoVoid() {
        when(subRepository.findByUserIdAndTopicRef(1L, "angular")).thenReturn(Mono.just(sub2));
        when(subRepository.delete(sub2)).thenReturn(Mono.empty());
        subscribtionServiceImpl.unsubscribeUserToTopic(1L, "angular").as(StepVerifier::create)
            .verifyComplete();

    }
}
