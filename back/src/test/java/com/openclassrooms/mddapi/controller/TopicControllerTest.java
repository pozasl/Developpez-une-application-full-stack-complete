package com.openclassrooms.mddapi.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.TopicService;

import reactor.core.publisher.Flux;

@WebFluxTest(TopicController.class)
public class TopicControllerTest {

    @Autowired
    WebTestClient webClient;

    @MockBean
    TopicService topicService;

    @Test
    void findAll_shouldReturnOk() throws Exception {

        Topic topic = new Topic();
        topic.setRef("java");
        topic.setName("Java");

        when(topicService.findAll()).thenReturn(Flux.just(topic));
        
        webClient.get().uri("/api/topics").exchange()
            .expectStatus().isOk()
            .expectBody()
                .jsonPath("@.[0].ref").isEqualTo("java")
                .jsonPath("@.[0].name").isEqualTo("Java");
    }
    
}
