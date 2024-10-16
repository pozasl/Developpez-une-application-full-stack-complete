package com.openclassrooms.mdd.posts_api.mapper;

import com.openclassrooms.mdd.api.model.NewPost;
import com.openclassrooms.mdd.api.model.Post;
import com.openclassrooms.mdd.posts_api.model.PostEntity;

import reactor.core.publisher.Mono;

/**
 * Post mapper
 */
public interface PostMapper {

   /**
    * Convert model to entity
    * @param model NewPost model
    * @return Post entity
    */
   PostEntity toEntity(NewPost model);

   /**
    * Convert entity to model
    * @param entity Post entity
    * @return Post model
    */
   Post toModel(PostEntity entity);

   /**
    * Convert entity to model reactively
    * @param entity Post entity
    * @return Post model
    */
   Mono<Post> toModel(Mono<PostEntity> entity);
}
