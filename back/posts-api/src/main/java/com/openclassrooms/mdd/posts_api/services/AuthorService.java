package com.openclassrooms.mdd.posts_api.services;

import com.openclassrooms.mdd.posts_api.model.AuthorEntity;

import reactor.core.publisher.Mono;

public interface AuthorService {
    Mono<AuthorEntity> updateAuthorByUserId(Long userId, AuthorEntity author);
}
