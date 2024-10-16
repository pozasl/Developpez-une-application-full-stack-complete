package com.openclassrooms.mdd.posts_api.mapper;

import com.openclassrooms.mdd.api.model.Topic;
import com.openclassrooms.mdd.posts_api.model.TopicEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Topic mapper
 */
public interface TopicMapper {
    
    /**
     * Convert entity to model
     *
     * @param entity Topic entity
     * @return Topic model
     */
    public Topic toModel(TopicEntity entity);

    /**
     * Convert entity to model reactively 
     *
     * @param entity Topic entity
     * @return Topic model
     */
    public Mono<Topic> toModel(Mono<TopicEntity> entity);

    /**
     * Convert entities to models reactively 
     *
     * @param entity Topic entities
     * @return Topic models
     */
    public Flux<Topic> toModel(Flux<TopicEntity> entity);

    /**
     * Convert model to Entity
     *
     * @param model Topic model
     * @return Topic entity
     */
    public TopicEntity toEntity(Topic model);
    
}
