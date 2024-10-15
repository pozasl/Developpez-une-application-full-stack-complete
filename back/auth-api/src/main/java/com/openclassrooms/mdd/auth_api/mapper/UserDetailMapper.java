package com.openclassrooms.mdd.auth_api.mapper;

import com.openclassrooms.mdd.api.model.User;
import com.openclassrooms.mdd.auth_api.model.UserDetailEntity;

/**
 * User dteil mapper
 */
public interface UserDetailMapper {
    /**
     * Convert UserDetail entity to User Model
     * @param userDetail the UserDetail entity
     * @return the User model
     */
    User toUserModel(UserDetailEntity userDetail);
}
