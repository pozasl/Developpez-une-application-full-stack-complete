package com.openclassrooms.mddapi.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class TopicTest {

    @Test
    void itShouldInstantiateWithoutArgs() {
        Topic topic = new Topic();
        assertThat(topic).isNotNull();
    }

    @Test
    void getterSetterShouldWork() {
        Topic topic = new Topic();
        topic.setRef("java");
        topic.setName("Java");
        assertThat(topic.getRef()).isEqualTo("java");
        assertThat(topic.getName()).isEqualTo("Java");
    }
    
}
