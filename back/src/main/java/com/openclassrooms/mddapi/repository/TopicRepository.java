package com.openclassrooms.mddapi.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Topic;

@Repository
public interface TopicRepository extends ReactiveMongoRepository<Topic, String> {

}
