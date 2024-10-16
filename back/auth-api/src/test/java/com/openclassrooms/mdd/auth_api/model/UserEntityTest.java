package com.openclassrooms.mdd.auth_api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserEntityTest {

    private UserEntity user;

    private LocalDateTime date = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
    private LocalDateTime date2 = LocalDateTime.of(2024, 1, 1, 1, 0, 0);

    @BeforeEach
    void setup() {
        user = new UserEntity(
            1L,
            "bob",
            "bob@test.com",
            "pass4321",
            date,
            date2
        );
    }

    @Test
    void itShouldInstantiateWithoutArg() {
        assertThat(new UserEntity()).isNotNull();
    }

    void itShouldInstantiateWithAllArgs() {
        assertThat(user).isNotNull();
    }

    @Test
    void testGetId() {
        assertThat(user.getId()).isEqualTo(1L);
    }

    @Test
    void testGetName() {
        assertThat(user.getName()).isEqualTo("bob");
    }

    @Test
    void testGetEmail() {
        assertThat(user.getEmail()).isEqualTo("bob@test.com");
    }

    @Test
    void testSetId() {
        user.setId(2L);
        assertThat(user.getId()).isEqualTo(2L);
    }

    @Test
    void testSetName() {
        user.setName("boby");
        assertThat(user.getName()).isEqualTo("boby");
    }

    @Test
    void testSetEmail() {
        user.setEmail("bob@smith.com");
        assertThat(user.getEmail()).isEqualTo("bob@smith.com");
    }

    @Test
    void testEquals() {
        UserEntity user2 = new UserEntity(
            1L,
            "bob",
            "bob@test.com",
            "pass4321",
            date,
            date2
        );
        assertThat(user).isEqualTo(user2);
        
    }

    @Test
    void testGetCreatedAt() {
        assertThat(user.getCreatedAt()).isEqualTo(date);
    }

    @Test
    void testGetUpdatedAt() {
        assertThat(user.getUpdatedAt()).isEqualTo(date2);
    }

    @Test
    void testHashCode() {
        int hashCode = 1503656930;
        assertThat(user.hashCode()).isEqualTo(hashCode);
    }

    @Test
    void testSetCreatedAt() {
        user.setCreatedAt(date2);
        assertThat(user.getCreatedAt()).isEqualTo(date2);
    }

    @Test
    void testSetUpdatedAt() {
        user.setUpdatedAt(date);
        assertThat(user.getCreatedAt()).isEqualTo(date);
    }

    @Test
    void testToString() {
        String str = "UserEntity(id=1, name=bob, email=bob@test.com, password=pass4321, createdAt=2024-01-01T00:00, updatedAt=2024-01-01T01:00)";
        assertThat(user.toString()).isEqualTo(str);
        
    }
}
