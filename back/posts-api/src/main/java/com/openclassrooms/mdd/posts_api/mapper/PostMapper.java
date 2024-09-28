package com.openclassrooms.mdd.posts_api.mapper;

import com.openclassrooms.mdd.api.model.NewPost;
import com.openclassrooms.mdd.api.model.Post;
import com.openclassrooms.mdd.posts_api.model.PostEntity;

import reactor.core.publisher.Mono;

public interface PostMapper {
   PostEntity toEntity(NewPost model);
   Post toModel(PostEntity entity);
   Mono<Post> toModel(Mono<PostEntity> entity);
}
