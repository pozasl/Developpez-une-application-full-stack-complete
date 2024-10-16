package com.openclassrooms.mdd.feeds_api.service;

import com.openclassrooms.mdd.feeds_api.model.FeedPostEntity;

import reactor.core.publisher.Flux;

/**
 * Feed service
 */
public interface FeedService {
    /**
     * Find feed's posts by feed's user id with ordered sorting
     * @param userId the feed's user id
     * @param sort the sort order ('asc' | 'desc')
     * @return the feed's posts
     */
    Flux<FeedPostEntity> findPostByUserId(Long userId, String sort);
}
