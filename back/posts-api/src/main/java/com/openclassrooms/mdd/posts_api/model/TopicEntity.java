package com.openclassrooms.mdd.posts_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record TopicEntity(
    @Id
    String ref,
    String name
) {
}