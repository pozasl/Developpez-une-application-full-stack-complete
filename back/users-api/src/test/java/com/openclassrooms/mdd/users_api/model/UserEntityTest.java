package com.openclassrooms.mdd.users_api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserEntityTest {

    private UserEntity user;

    private LocalDateTime date = LocalDateTime.now();

    @BeforeEach
    void setup() {
        user = new UserEntity(
            1L,
            "bob",
            "bob@test.com",
            "pass4321",
            date,
            date
        );
    }

    @Test
    void itShouldInstantiateWithoutArg() {
        assertThat(new UserEntity()).isNotNull();
    }

    void itShouldInstantiateWithNeededArgs() {
        assertThat(new UserEntity("bob","bob@test.com")).isNotNull();
    }

    void itShouldInstantiateWithArgs() {
       
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
}
