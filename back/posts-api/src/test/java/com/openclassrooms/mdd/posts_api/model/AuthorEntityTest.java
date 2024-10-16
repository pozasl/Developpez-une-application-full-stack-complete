package com.openclassrooms.mdd.posts_api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorEntityTest {

    private AuthorEntity bob;

    @BeforeEach
    void setup() {
        bob = new AuthorEntity("123456789098765432100001", 1L, "Bob", List.of(), List.of());
    }

    @Test
    void testEquals() {
        AuthorEntity bob2 = new AuthorEntity("123456789098765432100001", 1L, "Bob", List.of(), List.of());
        assertThat(bob).isEqualTo(bob2);
    }

    @Test
    void testHashCode() {
        int hashCode = 326767307;
        assertThat(bob.hashCode()).isEqualTo(hashCode);
    }

    @Test
    void testToString() {
        String bobStr = "AuthorEntity[id=123456789098765432100001, userId=1, userName=Bob, posts=[], replies=[]]";
        assertThat(bob.toString()).isEqualTo(bobStr);
    }

    @Test
    void testId() {
        assertThat(bob.id()).isEqualTo("123456789098765432100001");
    }

    @Test
    void testUserId() {
        assertThat(bob.userId()).isEqualTo(1L);
    }

    @Test
    void testUserName() {
        assertThat(bob.userName()).isEqualTo("Bob");
    }
}
