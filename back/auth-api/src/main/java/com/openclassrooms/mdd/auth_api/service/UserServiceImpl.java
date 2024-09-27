package com.openclassrooms.mdd.auth_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.api.model.NewUser;
import com.openclassrooms.mdd.auth_api.model.UserDetailEntity;
import com.openclassrooms.mdd.auth_api.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<UserDetailEntity> createUser(NewUser newUser) {
        String encodedPass = passwordEncoder.encode(newUser.getPassword());
        UserDetailEntity user = new UserDetailEntity(newUser.getName(), newUser.getEmail(), encodedPass);
        return existsByEmail(newUser.getEmail())
            .flatMap(exists -> exists ? Mono.error(new BadCredentialsException("email already used")) : userRepository.save(user));
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username).flatMap(user -> Mono.just(new UserDetailEntity(user)));
    }
    
}
