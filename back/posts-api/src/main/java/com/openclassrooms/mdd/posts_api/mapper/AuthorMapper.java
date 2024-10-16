package com.openclassrooms.mdd.posts_api.mapper;

import com.openclassrooms.mdd.api.model.Author;
import com.openclassrooms.mdd.posts_api.model.AuthorEntity;

/**
 * Author mapper
 */
public interface AuthorMapper {
    
    /**
     * Convert model to entity
     *
     * @param author Author model
     * @return Author entity
     */
    AuthorEntity toEntity(Author author);

    /**
     * Convert entity to model
     * @param author Author entity
     * @return Author model
     */
    Author toModel(AuthorEntity author);
}
