package com.openclassrooms.mdd.topicsapi.mapper;

import com.openclassrooms.mdd.api.model.Topic;
import com.openclassrooms.mdd.topicsapi.model.TopicEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TopicMapper {
    
    public Topic toModel(TopicEntity entity);
    public Mono<Topic> toModel(Mono<TopicEntity> entity);
    public Flux<Topic> toModel(Flux<TopicEntity> entity);
    
}
