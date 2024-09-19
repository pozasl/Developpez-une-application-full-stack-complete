package com.openclassrooms.mddapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
public class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicServiceImpl topicService;

    @Test
    void findAll_shoulReturnTopicsFlux() {
        Topic topic1 = new Topic();
        topic1.setRef("java");
        topic1.setName("Java");

        Topic topic2 = new Topic();
        topic2.setRef("angular");
        topic2.setName("Angular");

        when(topicRepository.findAll()).thenReturn(Flux.just(topic1, topic2));
        Flux<Topic> topics = topicService.findAll();
        assertThat(topics.blockFirst()).isEqualTo(topic1);
        assertThat(topics.blockLast()).isEqualTo(topic2);
    }

}
