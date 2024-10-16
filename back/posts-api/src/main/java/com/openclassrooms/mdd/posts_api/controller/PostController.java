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
import com.openclassrooms.mdd.api.model.NewReply;
import com.openclassrooms.mdd.api.model.Post;
import com.openclassrooms.mdd.api.model.ResponseMessage;
import com.openclassrooms.mdd.posts_api.mapper.PostMapper;
import com.openclassrooms.mdd.posts_api.mapper.PostMapperImpl;
import com.openclassrooms.mdd.posts_api.mapper.ReplyMapper;
import com.openclassrooms.mdd.posts_api.services.PostService;
import com.openclassrooms.mdd.posts_api.services.ReactiveProducerService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

/**
 * Posts API controller
 */
@RestController
@SecurityRequirement(name = "Authorization")
public class PostController implements PostsApiDelegate {

    private PostService postService;
    private PostMapper postMapper;
    private ReplyMapper replyMapper;
    private ReactiveProducerService producerService;

    PostController(
            PostService postService,
            PostMapperImpl postMapper,
            ReplyMapper replyMapper,
            ReactiveProducerService producerService) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.producerService = producerService;
        this.replyMapper = replyMapper;
    }

    /**
     * Get post by its id
     * @param id the post id
     * @return a post
     */
    @GetMapping("/api/posts/{id}")
    Mono<Post> getPostById(@PathVariable String id) {
        return postService.getPostById(id).map(postMapper::toModel);
    }

    /**
     * Create a post
     * @param newPost the new post
     * @param jwt Jwt authentication
     * @return The created post
     */
    @PostMapping("/api/posts")
    Mono<Post> createPost(@Valid @RequestBody NewPost newPost, @AuthenticationPrincipal Jwt jwt) {
        if (jwt.getClaim("userId").equals(newPost.getAuthor().getUserId())) {
            return postService.create(postMapper.toEntity(newPost))
                    .map(postMapper::toModel)
                    .doOnNext(post -> producerService.send(post));
        } else
            return Mono.error(new AccessDeniedException("Can't post with another user's id"));

    }

    /**
     * Create a reply in a post
     * @param id the post id
     * @param reply the new reply
     * @param jwt jwt authentication
     * @return a response message
     */
    @PostMapping("/api/posts/{id}/replies")
    Mono<ResponseMessage> createReply(
            @PathVariable String id,
            @Valid @RequestBody NewReply reply, @AuthenticationPrincipal Jwt jwt) {
        return postService.addReplyToPostId(id, replyMapper.toEntity(reply))
                .map(post -> new ResponseMessage().message("Reply added"));
    }

}
