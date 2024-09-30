package com.openclassrooms.mdd.feeds_api.model;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("feeds")
public class FeedPostEntity {

    @NonNull
    @Column(value ="user_id")
    private Long userId;

    @NonNull
    @Column(value ="post_ref")
    private String postRef;
    
}
