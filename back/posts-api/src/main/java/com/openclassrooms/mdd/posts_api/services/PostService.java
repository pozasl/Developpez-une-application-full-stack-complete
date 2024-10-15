package com.openclassrooms.mdd.posts_api.services;

import com.openclassrooms.mdd.posts_api.model.PostEntity;
import com.openclassrooms.mdd.posts_api.model.ReplyEntity;

import reactor.core.publisher.Mono;

/**
 * Post service
 */
public interface PostService {

    /**
     * Get post by its id
     *
     * @param id The post's id
     * @return The post
     */
    Mono<PostEntity> getPostById(String id);

    /**
     * Create a new Post and add it to the Author's post list
     *
     * @param postEntity
     * @return
     */
    Mono<PostEntity> create(PostEntity postEntity);

    /**
     * Add a Reply to a post and the post id to the author's replied post list
     *
     * @param id    the post id
     * @param reply the reply entity
     */
    Mono<PostEntity> addReplyToPostId(String id, ReplyEntity reply);
}
