package com.openclassrooms.mdd.posts_api.model;

public record AuthorEntity (
    Long userId,
    String userName
) {}