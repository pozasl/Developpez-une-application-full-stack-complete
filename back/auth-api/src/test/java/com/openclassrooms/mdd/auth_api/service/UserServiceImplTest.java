package com.openclassrooms.mdd.auth_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.openclassrooms.mdd.api.model.NewUser;
import com.openclassrooms.mdd.auth_api.model.UserDetailEntity;
import com.openclassrooms.mdd.auth_api.repository.UserRepository;
import com.openclassrooms.mdd.common.exception.ResourceNotFoundException;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private LocalDateTime date;

    private UserDetailEntity bob;

    @BeforeEach
    void setup() {
        date = LocalDateTime.now();
        bob = new UserDetailEntity("bob", "bob@test.com", "pass4321");
        bob.setId(1L);
        bob.setCreatedAt(date);
        bob.setUpdatedAt(date);
    }

    @Test
    void withExistingUser_existsByEmail_shouldReturnTrue() {
        when(userRepository.existsByEmail("bob@test.com")).thenReturn(Mono.just(true));
        userService.existsByEmail("bob@test.com").as(StepVerifier::create)
            .consumeNextWith(exists -> assertThat(exists).isEqualTo(true))
            .verifyComplete();
    }

    @Test
    void withNonExistingUser_existsByEmail_shouldReturnFalse() {
        when(userRepository.existsByEmail("boby@test.com")).thenReturn(Mono.just(false));
        userService.existsByEmail("boby@test.com").as(StepVerifier::create)
            .consumeNextWith(exists -> {
                assertThat(exists).isEqualTo(false);

            })
            .verifyComplete();
    }

    @Test
    void testCreateUser() {
        NewUser user = new NewUser("bob", "bob@test.com", "pass");
        when(userRepository.existsByEmail("bob@test.com")).thenReturn(Mono.just(false));
        when(userRepository.save(any())).thenReturn(Mono.just(bob));
        when(passwordEncoder.encode("pass")).thenReturn("pass1234");
        userService.createUser(user).as(StepVerifier::create)
            .consumeNextWith(u -> assertThat(u).isEqualTo(bob))
            .verifyComplete();
    }

    @Test
    void testFindByEmail() {
        when(userRepository.findByEmail("bob@test.com")).thenReturn(Mono.just(bob));
        userService.findByEmail("bob@test.com").as(StepVerifier::create)
        .consumeNextWith(u -> assertThat(u).isEqualTo(bob))
        .verifyComplete();
    }

    @Test
    void testFindByUsername() {
        when(userRepository.findByEmail("bob@test.com")).thenReturn(Mono.just(bob));
        userService.findByUsername("bob@test.com").as(StepVerifier::create)
        .consumeNextWith(u -> assertThat(u).isEqualTo(bob))
        .verifyComplete();
    }

    @Test
    void withKnownUserIdAndUnchangedPass_updateUser_shouldSaveAndreturnUpdatedUserBoby() {
        UserDetailEntity boby = new UserDetailEntity("bob", "bob@test.com", "");
        boby.setId(1L);
        UserDetailEntity bobySaved = new UserDetailEntity("bob", "bob@test.com", "pass4321");
        bobySaved.setId(1L);
        bobySaved.setCreatedAt(date);
        bobySaved.setUpdatedAt(date);
        when(userRepository.existsById(1L)).thenReturn(Mono.just(true));
        when(userRepository.save(any())).thenReturn(Mono.just(bobySaved));
        userService.updateUser(boby, false)
            .as(StepVerifier::create)
            .consumeNextWith(user -> {
                assertThat(user).isEqualTo(bobySaved);
            })
            .verifyComplete();
        verify(userRepository).save(any());
    }

    @Test
    void withKnownUserIdAndChangedPass_updateUser_shouldSaveAndreturnUpdatedUserBoby() {
        UserDetailEntity boby = new UserDetailEntity("bob", "bob@test.com", "pass");
        boby.setId(1L);
        UserDetailEntity bobySaved = new UserDetailEntity("bob", "bob@test.com", "pass4321");
        bobySaved.setId(1L);
        bobySaved.setCreatedAt(date);
        bobySaved.setUpdatedAt(date);
        when(userRepository.existsById(1L)).thenReturn(Mono.just(true));
        when(userRepository.save(any())).thenReturn(Mono.just(bobySaved));
        when(passwordEncoder.encode("pass")).thenReturn("pass4321");
        userService.updateUser(boby, true)
            .as(StepVerifier::create)
            .consumeNextWith(user -> {
                assertThat(user).isEqualTo(bobySaved);
            })
            .verifyComplete();
        verify(userRepository).save(any());
    }

    @Test
    void withUnknownUserIdAndUnchangedPass_updateUser_shouldSaveAndreturnUpdatedUserBoby() {
        UserDetailEntity boby = new UserDetailEntity("bob", "bob@test.com", "");
        boby.setId(9999L);
        UserDetailEntity bobySaved = new UserDetailEntity("bob", "bob@test.com", "pass4321");
        bobySaved.setId(1L);
        bobySaved.setCreatedAt(date);
        bobySaved.setUpdatedAt(date);
        when(userRepository.existsById(9999L)).thenReturn(Mono.just(false));
        userService.updateUser(boby, false)
            .as(StepVerifier::create)
            .consumeErrorWith(err -> {
                assertThat(err).isInstanceOf(ResourceNotFoundException.class);
            });
    }
}
