package com.openclassrooms.mdd.posts_api.services;

import com.openclassrooms.mdd.posts_api.model.PostEntity;

import reactor.core.publisher.Mono;

public interface PostService {
    Mono<PostEntity> getPostById(String id);
    Mono<PostEntity> create(PostEntity postEntity);
}
