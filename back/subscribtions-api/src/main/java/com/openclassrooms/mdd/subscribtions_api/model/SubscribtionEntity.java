package com.openclassrooms.mdd.subscribtions_api.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("subscribtions")
public record SubscribtionEntity (
    @Id
    String id,
    Long userId,
    String topicRef,
    Date date
) {}
