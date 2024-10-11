package com.openclassrooms.mdd.posts_api.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.openclassrooms.mdd.common.exception.ResourceNotFoundException;
import com.openclassrooms.mdd.posts_api.model.AuthorEntity;
import com.openclassrooms.mdd.posts_api.repository.AuthorRepository;

import reactor.core.publisher.Mono;

public class AuthorServiceImpl implements AuthorService{

    private AuthorRepository authorRepository;

    AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Mono<AuthorEntity> updateAuthorByUserId(Long userId, AuthorEntity author) {
        return authorRepository.findByUserId(userId)
            .switchIfEmpty(Mono.error(new ResourceNotFoundException()))
            .flatMap(foundAuthor -> {
                AuthorEntity updatedAuthor = new AuthorEntity(foundAuthor.id(), author.userId(), author.userName(), foundAuthor.posts(), foundAuthor.replies());
                return authorRepository.save(updatedAuthor);
            });
    }
    
}
