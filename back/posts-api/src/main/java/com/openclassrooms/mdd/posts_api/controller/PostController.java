package com.openclassrooms.mdd.posts_api.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mdd.api.PostsApiDelegate;
import com.openclassrooms.mdd.api.model.NewPost;
import com.openclassrooms.mdd.api.model.Post;
import com.openclassrooms.mdd.posts_api.mapper.PostMapper;
import com.openclassrooms.mdd.posts_api.mapper.PostMapperImpl;
import com.openclassrooms.mdd.posts_api.services.PostService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
public class PostController implements PostsApiDelegate{

    private PostService postService;
    private PostMapper postMapper;

    PostController(PostService postService, PostMapperImpl postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping("/api/posts/{id}")
    @SecurityRequirement(name = "Authorization")
    Mono<Post> getPostById(@PathVariable String id) {
        return postService.getPostById(id).map(postMapper::toModel);
    }

    @PostMapping("/api/posts")
    @SecurityRequirement(name = "Authorization")
    Mono<Post> createPost(@Valid @RequestBody NewPost newPost, @AuthenticationPrincipal Jwt jwt) {
        if (jwt.getClaim("userId").equals(newPost.getAuthor().getUserId())) {
            return postService.create(postMapper.toEntity(newPost))
            .map(postMapper::toModel);
        }
        else return Mono.error(new AccessDeniedException("Can't post with another user's id"));
        
    }
    
}
