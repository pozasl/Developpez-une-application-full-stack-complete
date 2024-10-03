package com.openclassrooms.mdd.auth_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mdd.api.AuthApiDelegate;
import com.openclassrooms.mdd.api.model.AuthInfo;
import com.openclassrooms.mdd.api.model.JwtInfo;
import com.openclassrooms.mdd.api.model.NewUser;
import com.openclassrooms.mdd.api.model.ResponseMessage;
import com.openclassrooms.mdd.auth_api.model.UserDetailEntity;
import com.openclassrooms.mdd.auth_api.service.JwtService;
import com.openclassrooms.mdd.auth_api.service.UserDetailsReactiveAuthenticationManager;
import com.openclassrooms.mdd.auth_api.service.UserService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
public class AuthController implements AuthApiDelegate{

    private ReactiveAuthenticationManager authenticationManager;
    private JwtService jwtService;
    private UserService userService;

    @Autowired
    AuthController(UserDetailsReactiveAuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/api/auth/register")
    Mono<ResponseMessage> register(@Valid @RequestBody NewUser newUser ) {
        return userService.createUser(newUser).then(Mono.just(new ResponseMessage().message("Account created")));
    }

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
}
