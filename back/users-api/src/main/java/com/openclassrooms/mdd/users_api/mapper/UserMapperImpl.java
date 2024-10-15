package com.openclassrooms.mdd.users_api.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.api.model.NewUser;
import com.openclassrooms.mdd.api.model.User;
import com.openclassrooms.mdd.users_api.model.UserEntity;

import reactor.core.publisher.Mono;

/**
 * User mapper implementation
 */
@Component
public class UserMapperImpl implements UserMapper{

    @Override
    public User toModel(UserEntity userEntity) {
        return new User()
            .id(userEntity.getId())
            .name(userEntity.getName())
            .email(userEntity.getEmail())
            .createdAt(convertDate(userEntity.getCreatedAt()))
            .updatedAt(convertDate(userEntity.getUpdatedAt()));
    }

    @Override
    public UserEntity toEntity(NewUser newUser) {
        return new UserEntity(newUser.getName(), newUser.getEmail(), newUser.getPassword());
    }

    @Override
    public Mono<User> toModel(Mono<UserEntity> userEntity) {
        return userEntity.map(this::toModel);
    }

    /**
     * Convert LocalDateTime to OffsetDateTime
     *
     * @param date LocalDateTime
     * @return OffsetDateTime
     */
    private OffsetDateTime convertDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        ZoneOffset zoneOffSet = ZoneId.systemDefault().getRules().getOffset(date);
        return date.atOffset(zoneOffSet);
    }
    
}
