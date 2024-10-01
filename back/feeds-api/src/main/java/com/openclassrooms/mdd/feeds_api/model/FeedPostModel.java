package com.openclassrooms.mdd.feeds_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FeedPostModel(
    @JsonProperty("user_id")
    Long userId,
    @JsonProperty("post_ref")
    String postRef
) {}
