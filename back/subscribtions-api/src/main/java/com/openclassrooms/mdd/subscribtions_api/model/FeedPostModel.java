package com.openclassrooms.mdd.subscribtions_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record FeedPostModel(
    @JsonProperty("user_id")
    Long userId,
    @JsonProperty("post_ref")
    String postRef,
    @JsonProperty("created_at")
    OffsetDateTime createdAt
) {}
