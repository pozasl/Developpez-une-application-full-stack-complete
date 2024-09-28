package com.openclassrooms.mdd.posts_api.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.openclassrooms.mdd.posts_api.model.PostEntity;
import com.openclassrooms.mdd.posts_api.repository.PostRepository;

import reactor.core.publisher.Mono;

public class PostServiceImpl implements PostService{

    private PostRepository postRepository;

    PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Mono<PostEntity> getPostById(String id) {
        return postRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException()));
    }
    
}
