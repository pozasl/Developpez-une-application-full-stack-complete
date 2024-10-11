package com.openclassrooms.mdd.users_api.service;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.common.exception.ResourceNotFoundException;
import com.openclassrooms.mdd.users_api.model.UserEntity;
import com.openclassrooms.mdd.users_api.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserEntity> findById(Long id) {
        return this.userRepository.findById(id).switchIfEmpty(Mono.error(new ResourceNotFoundException("No such user")));
    }

    @Override
    public Mono<UserEntity> updateUser(Long id, UserEntity newUser) {
        newUser.setId(id);
        return this.userRepository.existsById(id)
            .flatMap(exists -> exists ? userRepository.save(newUser) : Mono.error(new ResourceNotFoundException("No such user")));
    }

}
