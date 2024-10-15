package com.openclassrooms.mdd.posts_api.services;

import com.openclassrooms.mdd.posts_api.model.AuthorEntity;

import reactor.core.publisher.Mono;

/**
 * Author service
 */
public interface AuthorService {
    
    /**
     * Update Author by userid
     *
     * @param userId user id
     * @param author new author entity
     * @return updated author entity
     */
    Mono<AuthorEntity> updateAuthorByUserId(Long userId, AuthorEntity author);
}
