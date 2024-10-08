package com.openclassrooms.mdd.auth_api.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.api.model.User;
import com.openclassrooms.mdd.auth_api.model.UserDetailEntity;

@Component
public class UserDetailMapperImpl implements UserDetailMapper{

    @Override
    public User toUserModel(UserDetailEntity userEntity) {
        return new User()
            .id(userEntity.getId())
            .name(userEntity.getName())
            .email(userEntity.getEmail())
            .createdAt(convertDate(userEntity.getCreatedAt()))
            .updatedAt(convertDate(userEntity.getUpdatedAt()));
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
