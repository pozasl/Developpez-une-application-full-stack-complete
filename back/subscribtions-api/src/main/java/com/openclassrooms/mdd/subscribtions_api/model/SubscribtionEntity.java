package com.openclassrooms.mdd.subscribtions_api.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record SubscribtionEntity (
    Long userId,
    String topicRef,
    Date date
) {}
