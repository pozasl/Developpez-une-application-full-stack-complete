package com.openclassrooms.mdd.posts_api.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.api.model.Author;
import com.openclassrooms.mdd.api.model.NewPost;
import com.openclassrooms.mdd.api.model.Post;
import com.openclassrooms.mdd.posts_api.model.AuthorEntity;
import com.openclassrooms.mdd.posts_api.model.PostEntity;

import reactor.core.publisher.Mono;

@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public PostEntity toEntity(NewPost newPost) {
        return new PostEntity(
            null,
            newPost.getTitle(),
            newPost.getContent(),
            new Date(),
            new AuthorEntity(newPost.getAuthor().getUserId(), newPost.getAuthor().getUserName()),
            newPost.getTopic(),
            List.of());
    }

    @Override
    public Post toModel(PostEntity entity) {
        return new Post()
            .id(entity.id())
            .title(entity.title())
            .content(entity.content())
            .author(new Author()
                .userId(entity.author().userId())
                .userName(entity.author().userName())
            )
            .topic(entity.topic())
            .createdAt(convertDate(entity.date()));
    }

    @Override
    public Mono<Post> toModel(Mono<PostEntity> entityMono) {
        return entityMono.map(this::toModel);
    }

    private OffsetDateTime convertDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId =  ZoneOffset.systemDefault();
        return OffsetDateTime.ofInstant(instant, zoneId);
    }
    
}
