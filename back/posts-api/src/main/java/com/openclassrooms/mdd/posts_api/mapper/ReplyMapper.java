package com.openclassrooms.mdd.posts_api.mapper;

import com.openclassrooms.mdd.api.model.NewReply;
import com.openclassrooms.mdd.api.model.Reply;
import com.openclassrooms.mdd.posts_api.model.ReplyEntity;

/**
 * Reply mapper
 */
public interface ReplyMapper {

    /**
     * Convert model to entity
     *
     * @param reply NewReply model
     * @return Reply entity
     */
    ReplyEntity toEntity(NewReply reply);

    /**
     * Convert entity to model
     *
     * @param reply Reply entity
     * @return Rreply model
     */
    Reply toModel(ReplyEntity reply);    
}