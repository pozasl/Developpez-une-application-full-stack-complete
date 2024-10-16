package com.openclassrooms.mdd.posts_api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PostEntityTest {

    Date date = new Date(0L);
    AuthorEntity bob = new AuthorEntity("123456789098765432100001", 1L, "Bob", List.of(), List.of());
    TopicEntity topic = new TopicEntity("java", "Java", null);

    PostEntity post = new PostEntity("0123456789abcdef","Java in a Nutshell", "Java Bla bla bla", date, bob, topic, List.of());

    @Test
    void testAuthor() {
        assertThat(post.author()).isEqualTo(bob);
    }

    @Test
    void testContent() {
        assertThat(post.content()).isEqualTo("Java Bla bla bla");
    }

    @Test
    void testDate() {
        assertThat(post.date()).isEqualTo(date);
    }

    @Test
    void testEquals() {
        PostEntity post2 = new PostEntity("0123456789abcdef","Java in a Nutshell", "Java Bla bla bla", date, bob, topic, List.of());
        assertThat(post).isEqualTo(post2);
    }

    @Test
    void testHashCode() {
        int hashCode = -841182980;
        assertThat(post.hashCode()).isEqualTo(hashCode);
    }

    @Test
    void testId() {
        assertThat(post.id()).isEqualTo("0123456789abcdef");
    }

    @Test
    void testTitle() {
        assertThat(post.title()).isEqualTo("Java in a Nutshell");
    }

    @Test
    void testToString() {
        String postStr = "PostEntity[id=0123456789abcdef, title=Java in a Nutshell, content=Java Bla bla bla, date=Thu Jan 01 00:00:00 UTC 1970, author=AuthorEntity[id=123456789098765432100001, userId=1, userName=Bob, posts=[], replies=[]], topic=TopicEntity[ref=java, name=Java, description=null], replies=[]]";
        assertThat(post.toString()).isEqualTo(postStr);
    }

    @Test
    void testTopic() {
        assertThat(post.topic()).isEqualTo(topic);
    }
}
