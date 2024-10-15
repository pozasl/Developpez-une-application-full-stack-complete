package com.openclassrooms.mdd.posts_api.repository;

import com.mongodb.client.result.UpdateResult;
import com.openclassrooms.mdd.posts_api.model.ReplyEntity;

import reactor.core.publisher.Mono;

public interface ReplyPostRepository {

    /**
     * Add a reply to post by id
     *
     * @param postId the post'id
     * @param reply the reply Entity to add to the post's replies list
     * @return the update resulte as Mono
     */
    Mono<UpdateResult> addReplyToPostId(String postId, ReplyEntity reply);

    /**
     * Update Author's name in the topic's replies replied by author.
     *
     * @param postId the post'id
     * @param userId the reply'author userId to search
     * @param userName the userName to update
     * @return the update resulte as Mono
     */
    Mono<UpdateResult> findAndUpdatePostRepliesAuthorUserNameByPostId(String postId, Long userId, String userName);
    
}
