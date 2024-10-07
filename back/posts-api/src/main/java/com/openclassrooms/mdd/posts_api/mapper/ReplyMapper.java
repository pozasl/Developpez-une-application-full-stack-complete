package com.openclassrooms.mdd.posts_api.mapper;

import com.openclassrooms.mdd.api.model.NewReply;
import com.openclassrooms.mdd.api.model.Reply;
import com.openclassrooms.mdd.posts_api.model.ReplyEntity;

public interface ReplyMapper {
    ReplyEntity toEntity(NewReply reply);
    Reply toModel(ReplyEntity reply);    
}