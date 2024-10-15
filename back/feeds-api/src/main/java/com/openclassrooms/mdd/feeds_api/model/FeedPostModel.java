package com.openclassrooms.mdd.feeds_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

/**
 * Post reference model for a user's feed
 */
public record FeedPostModel(
    @JsonProperty("user_id")
    Long userId,
    @JsonProperty("post_ref")
    String postRef,
    @JsonProperty("created_at")
    OffsetDateTime createdAt
) {}
