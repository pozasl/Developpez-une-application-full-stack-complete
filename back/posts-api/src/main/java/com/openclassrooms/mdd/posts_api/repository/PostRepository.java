package com.openclassrooms.mdd.posts_api.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.posts_api.model.PostEntity;

@Repository
public interface PostRepository extends ReactiveMongoRepository<PostEntity, String>, ReplyPostRepository {

}
