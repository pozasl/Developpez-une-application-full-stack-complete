package com.openclassrooms.mdd.users_api.repository;

import com.openclassrooms.mdd.users_api.model.UserEntity;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import reactor.test.StepVerifier;

@Tag("S.I.T.")
@DataR2dbcTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private UserEntity bob = new UserEntity("bob", "bob@test.com", "pass4321");
    private UserEntity alice = new UserEntity("alice", "alice@test.com", "pass1234");

    @Test
    void findAll_ShouldReturnUsers() {
        
        userRepository.findAll()
            .as(StepVerifier::create)
            .assertNext(bob::equals)
            .assertNext(alice::equals)
            .verifyComplete();
    }

    @Test
    void findById_ShouldReturnUser() {
        userRepository.findById(1L)
            .as(StepVerifier::create)
            .assertNext(bob::equals)
            .verifyComplete();
    }

    @Test
    void findByEmail_ShouldReturnUser() {
        userRepository.findByEmail("bob@test.com")
            .as(StepVerifier::create)
            .assertNext(bob::equals)
            .verifyComplete();
    }

}
