package com.openclassrooms.mdd.posts_api.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TopicEntityTest {

    private TopicEntity topic;
    
    @BeforeEach
    void setup() {
        topic = new TopicEntity("java", "Java", "Java bla bla bla");
    }
    @Test
    void itShouldInstantiateWithArgs() {
        assertThat(topic).isNotNull();
    }

    @Test
    void getterShouldWork() {
        TopicEntity topic = new TopicEntity("java", "Java", "Java bla bla bla");
        assertThat(topic.ref()).isEqualTo("java");
        assertThat(topic.name()).isEqualTo("Java");
        assertThat(topic.description()).isEqualTo("Java bla bla bla");
    }
    
}
