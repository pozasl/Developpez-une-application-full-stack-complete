package com.openclassrooms.mdd.posts_api.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.posts_api.model.AuthorEntity;

import reactor.core.publisher.Mono;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<AuthorEntity, String>{
    @Query("{ 'userId' : ?0 }")
    Mono<AuthorEntity> findByUserId(Long userId);

    /**
     * find the Author by its userId, update its userName and return the number of updated document
     * @param userId
     * @param userName
     * @return
     */
    @Query("{ 'userId' : ?0 }")
    @Update("{ '$set' : { 'userName' : ?1 } }")
    Mono<Long> findAndUpdateUserNameByUserId(Long userId, String userName);
}
