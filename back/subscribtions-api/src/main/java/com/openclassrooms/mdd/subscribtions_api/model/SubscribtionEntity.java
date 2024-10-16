package com.openclassrooms.mdd.subscribtions_api.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Subscribtion entity
 */
@Document("subscribtions")
public record SubscribtionEntity (
    @Id
    String id,
    Long userId,
    String topicRef,
    Date date
) {}
