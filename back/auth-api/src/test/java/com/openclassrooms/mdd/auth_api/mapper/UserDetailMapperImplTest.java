package com.openclassrooms.mdd.auth_api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.mdd.api.model.User;
import com.openclassrooms.mdd.auth_api.model.UserDetailEntity;

@ExtendWith(MockitoExtension.class)
public class UserDetailMapperImplTest {

    @InjectMocks
    private UserDetailMapperImpl userMapper;

    private LocalDateTime date;
    private OffsetDateTime offDate;
    private UserDetailEntity userEntity;
    private User userModel;

    @BeforeEach
    void setup() {
        date = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        offDate = OffsetDateTime.of(date, ZoneId.systemDefault().getRules().getOffset(date));
        
        userEntity = new UserDetailEntity(
            "bob",
            "bob@test.com",
            "pass4321"
        );
        userEntity.setId(1L);
        userEntity.setCreatedAt(date);
        userEntity.setUpdatedAt(date);

        userModel = new User();
        userModel.setId(1L);
        userModel.setEmail("bob@test.com");
        userModel.setName("bob");
        userModel.setCreatedAt(offDate);
        userModel.setUpdatedAt(offDate);
    }
    
    @Test
    void testToUserModel() {
        User user = userMapper.toUserModel(userEntity);
        assertThat(user).isEqualTo(userModel);
    }
}
