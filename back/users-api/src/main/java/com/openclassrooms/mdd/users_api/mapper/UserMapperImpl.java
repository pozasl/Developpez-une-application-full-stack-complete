package com.openclassrooms.mdd.users_api.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.api.model.User;
import com.openclassrooms.mdd.users_api.model.UserEntity;

import reactor.core.publisher.Mono;

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
    public UserEntity toEntity(User user) {
        return new UserEntity(user.getId(), user.getName(), user.getEmail(), null, user.getCreatedAt().toLocalDateTime(), user.getUpdatedAt().toLocalDateTime());
    }

    @Override
    public Mono<User> toModel(Mono<UserEntity> userEntity) {
        return userEntity.map(this::toModel);
    }

    /**
     * Convert LocalDateTime to OffsetDateTime
     *
     * @param date
     * @return
     */
    private OffsetDateTime convertDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        ZoneOffset zoneOffSet = ZoneId.systemDefault().getRules().getOffset(date);
        return date.atOffset(zoneOffSet);
    }
    
}
