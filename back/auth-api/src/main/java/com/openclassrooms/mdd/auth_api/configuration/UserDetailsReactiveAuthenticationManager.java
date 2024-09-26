package com.openclassrooms.mdd.auth_api.configuration;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.auth_api.repository.UserRepository;

import reactor.core.publisher.Mono;

@Component
public class UserDetailsReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final UserRepository userRepository;

    UserDetailsReactiveAuthenticationManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication auth) {
       final String email = auth.getName();
        return userRepository.findByEmail(email)
            .filter(user -> user.getPassword().equals(auth.getCredentials()))
            .map(u -> new UsernamePasswordAuthenticationToken(u, u.getPassword(),u.getAuthorities()));
    }
}
