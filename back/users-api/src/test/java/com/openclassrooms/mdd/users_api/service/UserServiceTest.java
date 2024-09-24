package com.openclassrooms.mdd.users_api.service;

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
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.openclassrooms.mdd.users_api.model.UserEntity;
import com.openclassrooms.mdd.users_api.repository.UserRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private LocalDateTime date;

    private UserEntity bob;

    @BeforeEach
    void setup() {
        date = LocalDateTime.now();
        bob = new UserEntity(1L, "bob", "bob@test.com", "pass4321", date, date);
    }

    @Test
    void findById1L_shouldreturnUserBob() {
        when(userRepository.findById(1L)).thenReturn(Mono.just(bob));
        userService.findById(1L)
            .as(StepVerifier::create)
            .consumeNextWith(user -> {
                assertThat(user).isEqualTo(bob);
            })
            .verifyComplete();
        verify(userRepository).findById(1L);
    }

    @Test
    void withKnownUserId_updateUser_shouldSaveAndreturnUpdatedUserBoby() {
        UserEntity boby = new UserEntity("boby", "boby@test.com", "pass4321");
        UserEntity bobySaved = new UserEntity(1L, "boby", "boby@test.com", "pass4321", date, date);
        when(userRepository.existsById(1L)).thenReturn(Mono.just(true));
        when(userRepository.save(any())).thenReturn(Mono.just(bobySaved));
        userService.updateUser(1L, boby)
            .as(StepVerifier::create)
            .consumeNextWith(user -> {
                assertThat(user).isEqualTo(bobySaved);
            })
            .verifyComplete();
        verify(userRepository).save(any());
    }

    @Test
    void withUnknownUserId_UpdateUser_shouldThrowNotFoundException() {
        UserEntity boby = new UserEntity("boby", "boby@test.com", "pass4321");
        when(userRepository.existsById(9999L)).thenReturn(Mono.just(false));
        userService.updateUser(9999L, boby)
            .as(StepVerifier::create)
            .consumeErrorWith(e -> assertThat(e).isInstanceOf(NotFoundException.class));
        verify(userRepository).existsById(9999L);
    }
}
