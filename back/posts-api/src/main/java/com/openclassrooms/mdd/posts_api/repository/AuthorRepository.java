package com.openclassrooms.mdd.posts_api.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.posts_api.model.AuthorEntity;

import reactor.core.publisher.Mono;

/**
 * Author repository
 */
@Repository
public interface AuthorRepository extends ReactiveMongoRepository<AuthorEntity, String>{

    /**
     * Find author by user id
     *
     * @param userId the author's user id
     * @return author entity
     */
    @Query("{ 'userId' : ?0 }")
    Mono<AuthorEntity> findByUserId(Long userId);

    /**
     * find the Author by its userId, update its userName and return the number of updated document
     *
     * @param userId
     * @param userName
     * @return the author user id
     */
    @Query("{ 'userId' : ?0 }")
    @Update("{ '$set' : { 'userName' : ?1 } }")
    Mono<Long> findAndUpdateUserNameByUserId(Long userId, String userName);
}
