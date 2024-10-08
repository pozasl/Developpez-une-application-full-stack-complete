package com.openclassrooms.mdd.posts_api.mapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.openclassrooms.mdd.api.model.NewPost;
import com.openclassrooms.mdd.api.model.Post;
import com.openclassrooms.mdd.posts_api.model.PostEntity;

import reactor.core.publisher.Mono;

@Component
public class PostMapperImpl extends AbstractMapper implements PostMapper {

    private AuthorMapper authorMapper;
    private ReplyMapper replyMapper;
    private TopicMapper topicMapper;

    PostMapperImpl(AuthorMapper authorMapper, ReplyMapper replyMapper, TopicMapper topicMapper) {
        this.authorMapper = authorMapper;
        this.replyMapper = replyMapper;
        this.topicMapper = topicMapper;
    }

    @Override
    public PostEntity toEntity(NewPost newPost) {
        return new PostEntity(
            null,
            newPost.getTitle(),
            newPost.getContent(),
            new Date(),
            authorMapper.toEntity(newPost.getAuthor()),
            topicMapper.toEntity(newPost.getTopic()),
            List.of());
    }

    @Override
    public Post toModel(PostEntity entity) {
        return new Post()
            .id(entity.id())
            .title(entity.title())
            .content(entity.content())
            .author(authorMapper.toModel(entity.author()))
            .topic(topicMapper.toModel(entity.topic()))
            .createdAt(convertDate(entity.date()))
            .replies(entity.replies().stream().map(replyMapper::toModel).collect(Collectors.toList()))
            ;
    }

    @Override
    public Mono<Post> toModel(Mono<PostEntity> entityMono) {
        return entityMono.map(this::toModel);
    }
}
