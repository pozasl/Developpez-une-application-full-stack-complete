package com.openclassrooms.mdd.posts_api.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.api.model.NewReply;
import com.openclassrooms.mdd.api.model.Reply;
import com.openclassrooms.mdd.posts_api.model.ReplyEntity;

@Component
public class ReplyMapperImpl extends AbstractMapper implements ReplyMapper{

    private AuthorMapper authorMapper;

    ReplyMapperImpl(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    @Override
    public ReplyEntity toEntity(NewReply reply) {
        return new ReplyEntity(
            reply.getMessage(),
            new Date(),
            authorMapper.toEntity(reply.getAuthor()));
    }

    @Override
    public Reply toModel(ReplyEntity reply) {
        return new Reply()
            .author(authorMapper.toModel(reply.author()))
            .content(reply.content())
            .createdAt(convertDate(reply.date()));
    }
    
}
