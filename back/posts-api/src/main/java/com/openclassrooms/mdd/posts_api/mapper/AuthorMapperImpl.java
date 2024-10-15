package com.openclassrooms.mdd.posts_api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.api.model.Author;
import com.openclassrooms.mdd.posts_api.model.AuthorEntity;

/**
 * Author mapper's implementation
 */
@Component
public class AuthorMapperImpl  implements AuthorMapper{

    @Override
    public AuthorEntity toEntity(Author author) {
        return new AuthorEntity(null, author.getUserId(), author.getUserName(), List.of(), List.of());
    }

    @Override
    public Author toModel(AuthorEntity author) {
        return new Author().userId(author.userId()).userName(author.userName());
    }
    
}
