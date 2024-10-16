package com.openclassrooms.mdd.posts_api.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import com.openclassrooms.mdd.posts_api.model.PostEntity;

import reactor.core.publisher.Mono;

/**
 * Post repository
 */
@Repository
public interface PostRepository extends ReactiveMongoRepository<PostEntity, String>, ReplyPostRepository {

    /**
     * Update posts author's username find by the author's user id
     *
     * @param userId
     * @param userName
     * @return
     */
    @Query("{ 'author.userId' : ?0 }")
    @Update("{ '$set' : { 'author.userName' : ?1 } }")
    Mono<Long>findAndUpdatePostAuthorUserNameByAuthorUserId(Long userId, String userName);

}
