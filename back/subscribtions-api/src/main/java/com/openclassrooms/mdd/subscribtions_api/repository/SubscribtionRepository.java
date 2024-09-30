package com.openclassrooms.mdd.subscribtions_api.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.openclassrooms.mdd.subscribtions_api.model.SubscribtionEntity;

public interface SubscribtionRepository extends ReactiveMongoRepository<SubscribtionEntity, String>{
    
}
