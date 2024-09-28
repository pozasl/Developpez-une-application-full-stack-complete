package com.openclassrooms.mdd.posts_api.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.posts_api.model.PostEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PostRepository extends ReactiveMongoRepository<PostEntity, String>, ReplyPostRepository {

    Mono<PostEntity> findById(Long authorUserId);

    @Query("{ 'author.userId' : ?0 }")
    Flux<PostEntity> findByAuthorUserId(Long userId);

    @Query("{ 'author.userId' : ?0 }")
    @Update("{ '$set' : { 'author.userName' : '?1'} }")
    Mono<Void> updatePostAuthorByAuthorUserId(Long userId, String userName);

}
