package com.openclassrooms.mdd.auth_api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import com.openclassrooms.mdd.auth_api.model.UserDetailEntity;

import reactor.test.StepVerifier;

@Tag("S.I.T.")
@DataR2dbcTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepositoryIT {

    @Autowired
    UserRepository userRepository;

    private UserDetailEntity bob = new UserDetailEntity("bob", "bob@test.com", "pass4321");

    @Test
    void testExistsByEmail() {
        userRepository.existsByEmail("bob@test.com")
            .as(StepVerifier::create)
            .consumeNextWith(exists -> assertThat(exists).isEqualTo(true))
            .verifyComplete();
    }

    @Test
    void testFindByEmail() {
        userRepository.findByEmail("bob@test.com")
            .as(StepVerifier::create)
            .consumeNextWith(user -> assertThat(user.getName()).isEqualTo(bob.getName()))
            .verifyComplete();
    }
}
