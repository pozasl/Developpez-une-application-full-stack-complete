package com.openclassrooms.mdd.users_api.controller;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mdd.api.UsersApiDelegate;
import com.openclassrooms.mdd.api.model.NewUser;
import com.openclassrooms.mdd.api.model.User;
import com.openclassrooms.mdd.common.exception.ResourceNotFoundException;
import com.openclassrooms.mdd.users_api.mapper.UserMapper;
import com.openclassrooms.mdd.users_api.mapper.UserMapperImpl;
import com.openclassrooms.mdd.users_api.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @SecurityRequirement(name = "Authorization")
    public Mono<User> getUserById(@PathVariable Long id) {
        return userMapper.toModel(
            userService.findById(id))
            .switchIfEmpty(Mono.error(new ResourceNotFoundException("No such user"))
        );
    }

    @PutMapping("/api/user/{id}")
    @SecurityRequirement(name = "Authorization")
    public Mono<User> updateUserById(@PathVariable Long id, @Valid @RequestBody NewUser newUser, @AuthenticationPrincipal Jwt jwt) {
        if (jwt.getClaim("userId").equals(id))
        {
            return userMapper.toModel(
                userService.updateUser(id, userMapper.toEntity(newUser))
            );
        }
        else return Mono.error(new AccessDeniedException("Unauthorized access"));
    }
    
    
}
