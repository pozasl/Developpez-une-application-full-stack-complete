package com.openclassrooms.mdd.auth_api.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.openclassrooms.mdd.api.model.NewUser;
import com.openclassrooms.mdd.api.model.User;

public interface UserService extends UserDetailsService{
        User createUser(NewUser newUser);
}
