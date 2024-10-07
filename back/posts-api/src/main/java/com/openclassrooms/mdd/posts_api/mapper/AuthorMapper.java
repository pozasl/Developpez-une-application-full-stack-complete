package com.openclassrooms.mdd.posts_api.mapper;

import com.openclassrooms.mdd.api.model.Author;
import com.openclassrooms.mdd.posts_api.model.AuthorEntity;

public interface AuthorMapper {
    AuthorEntity toEntity(Author author);
    Author toModel(AuthorEntity author);
}
