package com.openclassrooms.mdd.posts_api.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorEntityTest {

    private AuthorEntity bob;

    @BeforeEach
    void setup() {
        bob = new AuthorEntity(1L, "Bob");
    }

    @Test
    void testEquals() {
        AuthorEntity bob2 = new AuthorEntity(1L, "Bob");
        assertThat(bob).isEqualTo(bob2);
    }

    @Test
    void testHashCode() {
        int hashCode = 66996;
        assertThat(bob.hashCode()).isEqualTo(hashCode);
    }

    @Test
    void testToString() {
        String bobStr = "Author[userId=1, userName=Bob]";
        assertThat(bob.toString()).isEqualTo(bobStr);
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
