package com.openclassrooms.mdd.users_api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.mdd.api.model.NewUser;
import com.openclassrooms.mdd.api.model.User;
import com.openclassrooms.mdd.users_api.model.UserEntity;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UserMapperImplTest {

    @InjectMocks
    private UserMapperImpl userMapper;

    private LocalDateTime date = LocalDateTime.now();
    private OffsetDateTime offDate = OffsetDateTime.of(date, ZoneId.systemDefault().getRules().getOffset(date));
    private UserEntity userEntity = new UserEntity("bob", "bob@test.com", "pass4321");
    private UserEntity userFullEntity = new UserEntity(1l, "bob", "bob@test.com", "pass4321", date, date);
    private NewUser newUserDto = new NewUser().name("bob").email("bob@test.com").password("pass4321");
    private User userDto = new User().id(1L).name("bob").email("bob@test.com").createdAt(offDate).updatedAt(offDate);


    @Test
    void model_toEntity_shouldReturnEntity() {
        UserEntity user = userMapper.toEntity(newUserDto);
        assertThat(user).isEqualTo(userEntity);
    }

    @Test
    void entity_toModel_shouldReturnModel() {
        User user = userMapper.toModel(userFullEntity);
        assertThat(user).isEqualTo(userDto);
    }

    @Test
    void monoEntity_toModel_shouldReturnMonoModel() {
        userMapper.toModel(Mono.just(userFullEntity))
            .as(StepVerifier::create)
            .consumeNextWith(user -> assertThat(user).isEqualTo(userDto))
            .verifyComplete();
    }


}
