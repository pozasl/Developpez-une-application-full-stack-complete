package com.openclassrooms.mdd.posts_api.model;

import java.util.Date;

public record ReplyEntity(
    String content,
    Date date,
    AuthorEntity author
) {
}