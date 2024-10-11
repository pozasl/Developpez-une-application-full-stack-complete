package com.openclassrooms.mdd.subscribtions_api.service;

import java.util.Date;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.common.exception.ResourceNotFoundException;
import com.openclassrooms.mdd.subscribtions_api.model.SubscribtionEntity;
import com.openclassrooms.mdd.subscribtions_api.repository.SubscribtionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SubscribtionServiceImpl implements SubscribtionService {

    private SubscribtionRepository subRepository;

    SubscribtionServiceImpl(SubscribtionRepository subRepository) {
        this.subRepository = subRepository;
    }

    @Override
    public Mono<SubscribtionEntity> subscribeUserToTopic(Long userId, String topicRef) {
        SubscribtionEntity sub = new SubscribtionEntity(null, userId, topicRef, new Date());
        return subRepository.findByUserIdAndTopicRef(userId, topicRef)
                .switchIfEmpty(subRepository.save(sub));
    }

    @Override
    public Mono<Void> unsubscribeUserToTopic(Long userId, String topicRef) {
        return subRepository.findByUserIdAndTopicRef(userId, topicRef)
        .switchIfEmpty(Mono.error(new ResourceNotFoundException("No suscribed user found")))
        .flatMap(sub -> subRepository.delete(sub));
    }

    @Override
    public Flux<SubscribtionEntity> findSubsByUserId(Long userId) {
        return subRepository.findByUserId(userId);
    }

    @Override
    public Flux<SubscribtionEntity> findSubsByTopicRef(String topicRef) {
        return subRepository.findByTopicRef(topicRef);
    }

}
