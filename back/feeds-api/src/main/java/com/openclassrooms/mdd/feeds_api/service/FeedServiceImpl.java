package com.openclassrooms.mdd.feeds_api.service;

import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.feeds_api.model.FeedPostEntity;
import com.openclassrooms.mdd.feeds_api.repository.FeedRepository;

import reactor.core.publisher.Flux;

@Service
public class FeedServiceImpl implements FeedService{

    private FeedRepository feedRepository;

    FeedServiceImpl(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Flux<FeedPostEntity> findPostByUserId(Long userId) {
        return feedRepository.findByUserId(userId);
    }
    
}
