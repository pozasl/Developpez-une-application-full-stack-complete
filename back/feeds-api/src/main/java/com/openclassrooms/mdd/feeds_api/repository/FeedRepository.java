package com.openclassrooms.mdd.feeds_api.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mdd.feeds_api.model.FeedPostEntity;

import reactor.core.publisher.Flux;

@Repository
public interface FeedRepository extends R2dbcRepository<FeedPostEntity, Long>{

    Flux<FeedPostEntity>findByUserId(long userId);
    Flux<FeedPostEntity>findByUserIdOrderByCreatedAtDesc(long userId);
    Flux<FeedPostEntity>findByUserIdOrderByCreatedAtAsc(long userId);
}