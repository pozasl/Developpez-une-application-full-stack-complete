package com.openclassrooms.mdd.posts_api.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Topic entity
 */
@Document("topics")
public record TopicEntity(
    String ref,
    String name, 
    String description
) {}
