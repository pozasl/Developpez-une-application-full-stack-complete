package com.openclassrooms.mdd.feeds_api.service;

import com.openclassrooms.mdd.feeds_api.model.FeedPostEntity;

import reactor.core.publisher.Flux;

public interface FeedService {
    Flux<FeedPostEntity> findPostByUserId(Long userId, String sort);
}
