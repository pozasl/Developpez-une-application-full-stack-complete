package com.openclassrooms.mdd.auth_api.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mdd.api.AuthApiDelegate;
import com.openclassrooms.mdd.api.model.AuthInfo;
import com.openclassrooms.mdd.api.model.JwtInfo;
import com.openclassrooms.mdd.api.model.NewUser;
import com.openclassrooms.mdd.api.model.ResponseMessage;
import com.openclassrooms.mdd.auth_api.service.JwtService;
import com.openclassrooms.mdd.auth_api.service.UserService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
public class AuthController implements AuthApiDelegate{

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private UserService userService;

    AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    Mono<ResponseMessage> register(@Valid @RequestBody NewUser newUser ) {
        return null;
    }

    @PostMapping
    Mono<JwtInfo> login(@Valid @RequestBody AuthInfo authInfo ) {
        return null;
    }
    
}
