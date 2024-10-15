package com.openclassrooms.mdd.auth_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mdd.api.AuthApiDelegate;
import com.openclassrooms.mdd.api.model.AuthInfo;
import com.openclassrooms.mdd.api.model.Author;
import com.openclassrooms.mdd.api.model.JwtInfo;
import com.openclassrooms.mdd.api.model.NewMe;
import com.openclassrooms.mdd.api.model.NewUser;
import com.openclassrooms.mdd.api.model.ResponseMessage;
import com.openclassrooms.mdd.api.model.User;
import com.openclassrooms.mdd.auth_api.mapper.UserDetailMapper;
import com.openclassrooms.mdd.auth_api.model.UserDetailEntity;
import com.openclassrooms.mdd.auth_api.service.JwtService;
import com.openclassrooms.mdd.auth_api.service.ReactiveProducerService;
import com.openclassrooms.mdd.auth_api.service.UserDetailsReactiveAuthenticationManager;
import com.openclassrooms.mdd.auth_api.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

/**
 * Authentication controller
 */
@RestController
public class AuthController implements AuthApiDelegate{

    private ReactiveAuthenticationManager authenticationManager;
    private JwtService jwtService;
    private UserService userService;
    private UserDetailMapper userDetailMapper;
    private ReactiveProducerService producerService;

    @Autowired
    AuthController(
        UserDetailsReactiveAuthenticationManager authenticationManager,
        JwtService jwtService,
        UserService userService,
        UserDetailMapper userDetailMapper,
        ReactiveProducerService producerService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.userDetailMapper = userDetailMapper;
        this.producerService = producerService;
    }

    /**
     * Register a new User
     *
     * @param newUser the new user tyo register
     * @return a response message
     */
    @PostMapping("/api/auth/register")
    Mono<ResponseMessage> register(@Valid @RequestBody NewUser newUser ) {
        return userService.createUser(newUser).then(Mono.just(new ResponseMessage().message("Account created")));
    }

    /**
     * Authenticate a user with login and password
     *
     * @param authInfo The user login and password
     * @return A response with a JWT token and the user id
     */
    @PostMapping("/api/auth/login")
    Mono<JwtInfo> login(@Valid @RequestBody AuthInfo authInfo ) {
        return authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authInfo.getEmail(), authInfo.getPassword())
        ).flatMap(auth ->{
            UserDetailEntity user = (UserDetailEntity) auth.getPrincipal();
            return Mono.just(new JwtInfo()
                .token(jwtService.generateToken(auth))
                .userId(user.getId())
            );
        });
    }

    /**
     * Authenticated user info
     *
     * @param auth the jwt authentication
     * @return User's info
     */
    @GetMapping("/api/auth/me")
    @SecurityRequirement(name = "Authorization")
    Mono<User> getMe(Authentication auth) {
        return userService.findByEmail(auth.getName()).map(userDetailMapper::toUserModel);
    }

    /**
     * Update the user credential
     *
     * @param me The new User credential
     * @param auth The jwt authentication 
     * @return the new jwt with updated credential and the user id
     */
    @PutMapping("/api/auth/me")
    @SecurityRequirement(name = "Authorization")
    Mono<JwtInfo> updateMe(@RequestBody NewMe me, Authentication auth) {
        return userService.findByEmail(auth.getName())
            .flatMap(user -> {
                Boolean changePass = me.getPassword() != null && !me.getPassword().isBlank();
                user.setName(me.getName());
                user.setEmail(me.getEmail());
                if (changePass) {
                    user.setPassword(me.getPassword());
                }
                return userService.updateUser(user, changePass).map(
                    updatedUser -> {
                        JwtInfo jwtInfo = new JwtInfo();
                        jwtInfo.setToken(jwtService.generateTokenFromUserdetail(updatedUser));
                        jwtInfo.setUserId(updatedUser.getId());
                        return jwtInfo;
                    }
                ).doOnNext(post -> producerService.send(
                    new Author().userId(user.getId()).userName(user.getName())
                ));

        });
    }
}
