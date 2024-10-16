package com.openclassrooms.mdd.posts_api.repository;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;
import com.openclassrooms.mdd.posts_api.model.PostEntity;
import com.openclassrooms.mdd.posts_api.model.ReplyEntity;

import reactor.core.publisher.Mono;

/**
 * Reply-Post repository implementation
 */
public class PostRepositoryImpl implements ReplyPostRepository{

    private ReactiveMongoTemplate template;

    PostRepositoryImpl(ReactiveMongoTemplate template) {
        this.template = template;
    }

    @Override
    public Mono<UpdateResult> addReplyToPostId(String postId, ReplyEntity reply) {
        Query query = Query.query(Criteria.where("id").is(postId));
        Update update = new Update().push("replies", reply);
        return template.updateFirst(query, update, PostEntity.class);
    }

    
    @Override
    public Mono<UpdateResult> findAndUpdatePostRepliesAuthorUserNameByPostId(String postId, Long userId, String userName) {
        Query query = Query.query(Criteria.where("id").is(postId));
        Update update = new Update().set("replies.$[reply].author.userName", userName);
        update.filterArray("reply.author.userId", userId);
        return template.updateMulti(query, update, PostEntity.class);
    }
}
