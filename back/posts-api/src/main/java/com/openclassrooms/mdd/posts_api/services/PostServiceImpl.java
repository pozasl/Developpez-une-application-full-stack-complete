package com.openclassrooms.mdd.posts_api.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.posts_api.model.PostEntity;
import com.openclassrooms.mdd.posts_api.repository.PostRepository;

import reactor.core.publisher.Mono;

@Service
public class PostServiceImpl implements PostService{

    private PostRepository postRepository;

    PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Mono<PostEntity> getPostById(String id) {
        return postRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException()));
    }

    @Override
    public Mono<PostEntity> create(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }
    
}
