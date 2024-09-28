package com.openclassrooms.mdd.posts_api.repository;

import com.mongodb.client.result.UpdateResult;
import com.openclassrooms.mdd.posts_api.model.ReplyEntity;

import reactor.core.publisher.Mono;

public interface ReplyPostRepository {

    Mono<UpdateResult> addReplyToPostId(String postId, ReplyEntity reply1);
    
}
