package com.openclassrooms.mdd.feeds_api.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.feeds_api.model.FeedPostEntity;

import reactor.core.publisher.Flux;

/**
 * Feed repository
 */
@Repository
public interface FeedRepository extends R2dbcRepository<FeedPostEntity, Long>{

    /**
     * Find feed's posts by feed's user id
     *
     * @param userId
     * @return
     */
    Flux<FeedPostEntity>findByUserId(long userId);

    /**
     * Find feed's posts by feed's user id ordered by date ascending
     *
     * @param userId
     * @return
     */
    Flux<FeedPostEntity>findByUserIdOrderByCreatedAtDesc(long userId);

    /**
     * Find feed's posts by feed's user id ordered by date descending
     *
     * @param userId
     * @return
     */
    Flux<FeedPostEntity>findByUserIdOrderByCreatedAtAsc(long userId);
}