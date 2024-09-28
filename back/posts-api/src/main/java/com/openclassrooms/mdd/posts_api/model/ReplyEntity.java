package com.openclassrooms.mdd.posts_api.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record ReplyEntity(
    String content,
    Date date,
    AuthorEntity author
) {
}