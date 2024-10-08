package com.openclassrooms.mdd.posts_api.mapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.api.model.Topic;
import com.openclassrooms.mdd.posts_api.model.TopicEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TopicMapperImpl implements TopicMapper{

    @Override
    public Topic toModel(TopicEntity entity) {
        return new Topic()
            .ref(entity.ref())
            .name(entity.name())
            .description(entity.description());
    }

    @Override
    public Mono<Topic> toModel(Mono<TopicEntity> entityMono) {
        return entityMono.map(entity ->this.toModel(entity));
    }

    @Override
    public Flux<Topic> toModel(Flux<TopicEntity> entitiesFlux) {
        return entitiesFlux.map(entity ->this.toModel(entity));
    }

    @Override
    public TopicEntity toEntity(Topic model) {
        return new TopicEntity(model.getRef(), model.getName(), model.getDescription());
    }
   
}
