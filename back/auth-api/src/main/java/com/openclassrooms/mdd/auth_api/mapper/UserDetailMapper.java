package com.openclassrooms.mdd.auth_api.mapper;

import com.openclassrooms.mdd.api.model.User;
import com.openclassrooms.mdd.auth_api.model.UserDetailEntity;

public interface UserDetailMapper {
    User toUserModel(UserDetailEntity userDetail);
}
