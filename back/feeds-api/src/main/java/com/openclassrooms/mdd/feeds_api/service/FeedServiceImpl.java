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
    public Flux<FeedPostEntity> findPostByUserId(Long userId, String sort) {
        Flux<FeedPostEntity> feedPostsFlux;
        switch (sort) {
            case "desc":
                feedPostsFlux = feedRepository.findByUserIdOrderByCreatedAtDesc(userId);
                break;

            case "asc":
                feedPostsFlux = feedRepository.findByUserIdOrderByCreatedAtAsc(userId);
                break;
        
            default:
                feedPostsFlux = feedRepository.findByUserId(userId);
            break;
        }
        return feedPostsFlux;
    }
    
}
