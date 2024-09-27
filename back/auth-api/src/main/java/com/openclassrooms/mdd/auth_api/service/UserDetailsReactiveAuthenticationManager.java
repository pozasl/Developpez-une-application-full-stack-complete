package com.openclassrooms.mdd.auth_api.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.auth_api.repository.UserRepository;

import reactor.core.publisher.Mono;

@Component
public class UserDetailsReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserDetailsReactiveAuthenticationManager(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication auth) {
       final String email = auth.getName();
        return userRepository.findByEmail(email)
            .filter(user -> {
                return passwordEncoder.matches(auth.getCredentials().toString(), user.getPassword());
            })
            .switchIfEmpty(Mono.error(
                new BadCredentialsException("Wrong email or password")))
            .map(u -> new UsernamePasswordAuthenticationToken(u, u.getPassword(),u.getAuthorities()));
    }
}
