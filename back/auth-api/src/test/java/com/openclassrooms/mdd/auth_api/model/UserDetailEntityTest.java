package com.openclassrooms.mdd.auth_api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserDetailEntityTest {

    private UserDetailEntity user;

    @BeforeEach
    void setup() {
        user = new UserDetailEntity(
            "bob",
            "bob@test.com",
            "pass4321"
        );
    }

    @Test
    void testGetAuthorities() {
        GrantedAuthority auth = new SimpleGrantedAuthority("USER");
        assertThat(user.getAuthorities()).hasSize(1);
        assertThat(user.getAuthorities()).isEqualTo(List.of(auth));
    }

    @Test
    void testGetUsername() {
        assertThat(user.getUsername()).isEqualTo("bob@test.com");
    }
}
