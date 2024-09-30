package com.openclassrooms.mdd.subscribtions_api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SubscribtionEntityTest {

    private SubscribtionEntity sub;
    private Date date;

    @BeforeEach
    void setup() {
        date = new Date(0L);
        sub = new SubscribtionEntity(1L, "java", date);
    }

    @Test
    void testDate() {
        assertThat(sub.date()).isEqualTo(date);
    }

    @Test
    void testEquals() {
        SubscribtionEntity sub2 = new SubscribtionEntity(1L, "java", date);
        assertThat(sub).isEqualTo(sub2);
    }

    @Test
    void testHashCode() {
        int hashCode = 100900319;
        assertThat(sub.hashCode()).isEqualTo(hashCode);
    }

    @Test
    void testToString() {
        String str = "SubscribtionEntity[userId=1, topicRef=java, date=Thu Jan 01 00:00:00 UTC 1970]";
        assertThat(sub.toString()).isEqualTo(str);
    }

    @Test
    void testTopicRef() {
        assertThat(sub.topicRef()).isEqualTo("java");
    }

    @Test
    void testUserId() {
        assertThat(sub.userId()).isEqualTo(1L);
    }
}
