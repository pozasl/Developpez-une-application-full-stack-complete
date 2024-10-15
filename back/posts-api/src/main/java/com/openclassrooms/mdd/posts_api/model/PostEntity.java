package com.openclassrooms.mdd.posts_api.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Post entity
 */
@Document("posts")
public record PostEntity (
    @Id
    String id,
    String title,
    String content,
    Date date,
    AuthorEntity author,
    TopicEntity topic,
    List<ReplyEntity> replies
) {}