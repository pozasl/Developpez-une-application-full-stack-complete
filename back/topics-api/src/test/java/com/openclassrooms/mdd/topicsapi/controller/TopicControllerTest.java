package com.openclassrooms.mdd.topicsapi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.openclassrooms.mdd.api.model.Topic;
import com.openclassrooms.mdd.topicsapi.mapper.TopicMapper;
import com.openclassrooms.mdd.topicsapi.model.TopicEntity;
import com.openclassrooms.mdd.topicsapi.service.TopicService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(TopicController.class)
public class TopicControllerTest {

    @Autowired
    WebTestClient webClient;

    @MockBean
    TopicService topicService;

    @MockBean
    TopicMapper topicMapper;

    private TopicEntity topicEntity;
    private Topic topic;

    @BeforeEach
    void setup() {
        topicEntity = new TopicEntity();
        topicEntity.setRef("java");
        topicEntity.setName("Java");
        topicEntity.setDescription("Java's description");

        topic = new Topic().ref("java").name("Java").description("Java's description");

        when(topicMapper.toModel(topicEntity)).thenReturn(topic);
    }

    @Test
    void findAll_shouldReturnOk() throws Exception {

        
        Flux<TopicEntity> entitiesFlux = Flux.just(topicEntity);

        when(topicService.findAll()).thenReturn(entitiesFlux);
        when(topicMapper.toModel(entitiesFlux)).thenReturn(Flux.just(topic));
        
        webClient.mutateWith(
                mockJwt().jwt(jwt -> jwt
                    .claim("userId", 1)
                    .claim("sub", "bob")
                )
            )
            .get().uri("/api/topics").exchange()
            .expectStatus().isOk()
            .expectBody()
                .jsonPath("@.[0].ref").isEqualTo("java")
                .jsonPath("@.[0].name").isEqualTo("Java")
                .jsonPath("@.[0].description").isEqualTo("Java's description");
    }

    @Test
    void withExistingTopicRef_findByRef_shouldReturnOk() throws Exception {

        Mono<TopicEntity> entityMono = Mono.just(topicEntity);

        when(topicService.findByRef("java")).thenReturn(entityMono);
        when(topicMapper.toModel(entityMono)).thenReturn(Mono.just(topic));
        
        webClient.mutateWith(
                mockJwt().jwt(jwt -> jwt
                    .claim("userId", 1)
                    .claim("sub", "bob")
                )
            )
            .get().uri("/api/topics/java").exchange()
            .expectStatus().isOk()
            .expectBody()
                .jsonPath("@.ref").isEqualTo("java")
                .jsonPath("@.name").isEqualTo("Java")
                .jsonPath("@.description").isEqualTo("Java's description");
    }
    
}
