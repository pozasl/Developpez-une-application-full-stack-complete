package com.openclassrooms.mdd.users_api.service;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.users_api.model.UserEntity;
import com.openclassrooms.mdd.users_api.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserEntity> findById(Long id) {
        return this.userRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException()));
    }

    @Override
    public Mono<UserEntity> updateUser(UserEntity user) {
        return this.userRepository.findById(user.getId())
        .flatMap(oldUser -> {
            // We only change username and email
            oldUser.setName(user.getName());
            oldUser.setEmail(user.getEmail());
            return userRepository.save(oldUser);
        })
        .switchIfEmpty(Mono.error(new NotFoundException()));
    }

}
