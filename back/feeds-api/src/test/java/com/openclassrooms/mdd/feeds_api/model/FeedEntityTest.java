package com.openclassrooms.mdd.feeds_api.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FeedEntityTest {

    private FeedPostEntity feedPost;

    @BeforeEach
    void setup() {
        feedPost = new FeedPostEntity(1L, "12345678909876543210abcdef");
    }

    @Test
    void testEquals() {
        FeedPostEntity feedPost2 = new FeedPostEntity(1L,"12345678909876543210abcdef");
        assertThat(feedPost).isEqualTo(feedPost2);
    }


    @Test
    void testGetPostId() {
        assertThat(feedPost.getPostRef()).isEqualTo("12345678909876543210abcdef");
    }

    @Test
    void testGetUserId() {
        assertThat(feedPost.getUserId()).isEqualTo(1L);
    }

    @Test
    void testHashCode() {
        int hashCode = 1075434029;
        assertThat(feedPost.hashCode()).isEqualTo(hashCode);
    }


    @Test
    void testSetPostRef() {
        feedPost.setPostRef("12345678909876543210abcde0");
        assertThat(feedPost.getPostRef()).isEqualTo("12345678909876543210abcde0");
    }

    @Test
    void testSetUserId() {
        feedPost.setUserId(4L);
        assertThat(feedPost.getUserId()).isEqualTo(4L);
    }

    @Test
    void testToString() {
        String str = "FeedPostEntity(userId=1, postRef=12345678909876543210abcdef)";
        assertThat(feedPost.toString()).isEqualTo(str);
    }
}
