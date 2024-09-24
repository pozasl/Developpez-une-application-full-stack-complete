package com.openclassrooms.mdd.users_api.controller;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mdd.api.UsersApi;
import com.openclassrooms.mdd.api.UsersApiDelegate;
import com.openclassrooms.mdd.api.model.NewUser;
import com.openclassrooms.mdd.api.model.User;
import com.openclassrooms.mdd.users_api.mapper.UserMapper;
import com.openclassrooms.mdd.users_api.mapper.UserMapperImpl;
import com.openclassrooms.mdd.users_api.service.UserService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
public class UserController implements UsersApiDelegate {

    private UserService userService;
    private UserMapper userMapper;

    UserController(UserService userService, UserMapperImpl userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/api/user/{id}")
    public Mono<User> getUserById(@PathVariable Long id) {
        return userMapper.toModel(
            userService.findById(id))
            .switchIfEmpty(Mono.error(new NotFoundException())
        );
    }

    @PutMapping("/api/user/{id}")
    public Mono<User> updateUserById(@PathVariable Long id, @Valid @RequestBody NewUser newUser) {
        return userMapper.toModel(
            userService.updateUser(id, userMapper.toEntity(newUser))
        );
    }
    
    
}
