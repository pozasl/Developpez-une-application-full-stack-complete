package com.openclassrooms.mdd.posts_api.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("authors")
public record AuthorEntity (
    @Id
    String id,
    Long userId,
    String userName,
    List<String> posts,
    List<String> replies
) {}