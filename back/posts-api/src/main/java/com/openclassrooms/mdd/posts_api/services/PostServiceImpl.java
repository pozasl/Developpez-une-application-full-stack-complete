package com.openclassrooms.mdd.posts_api.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.posts_api.model.AuthorEntity;
import com.openclassrooms.mdd.posts_api.model.PostEntity;
import com.openclassrooms.mdd.posts_api.model.ReplyEntity;
import com.openclassrooms.mdd.posts_api.repository.AuthorRepository;
import com.openclassrooms.mdd.posts_api.repository.PostRepository;

import reactor.core.publisher.Mono;

@Service
public class PostServiceImpl implements PostService{

    private PostRepository postRepository;
    private AuthorRepository authorRepository;

    PostServiceImpl(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Mono<PostEntity> getPostById(String id) {
        return postRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException()));
    }

    /**
     * Create a new Post and add it to the Author's post list
     */
    @Override
    public Mono<PostEntity> create(PostEntity postEntity) {

        return postRepository.save(postEntity)
        .flatMap(post -> {
            System.out.println(post.toString());
            return authorRepository.findById(post.author().id())
            .switchIfEmpty(authorRepository.save(post.author()))
            .flatMap(author -> {
                System.out.println("1 -> " + author.toString());
                AuthorEntity updatedAuthor = addPostIdToAuthorPosts(post.id(), author);
                System.out.println("2 -> " + updatedAuthor.toString());
                return authorRepository.save(updatedAuthor);
            })
            .map((a) -> {
                System.out.println("3 -> " + a.toString());
                return post;
            });
        });
    }

    /**
     * Add a Reply to a post and the post id to the author's replied post list
     *
     * @param id the post id
     * @param reply the reply entity
     */
    @Override
    public Mono<PostEntity> addReplyToPostId(String id, ReplyEntity reply) {
        return postRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException()))
            .flatMap((post) -> {
            post.replies().add(reply);
            return postRepository.save(post)
            .flatMap(p -> {
                return authorRepository.findById(post.author().id())
                .switchIfEmpty(authorRepository.save(post.author()))
                .flatMap( author -> {
                    AuthorEntity updatedAuthor = addPostIdToAuthorRepliedPost(post.id(), author);
                    return authorRepository.save(updatedAuthor);
                })
                .then(Mono.just(post));
            });
        });
    }

    /**
     * Add a post id to post list of an author
     *
     * @param postId
     * @param author
     * @return
     */
    private AuthorEntity addPostIdToAuthorPosts(String postId, AuthorEntity author) {
        List<String> postIds = Stream.concat(
            author.posts().stream(),
            Stream.of(postId)
        ).collect(Collectors.toList());
        return new AuthorEntity(
            author.id(),
            author.userId(),
            author.userName(),
            postIds,
            author.replies()
        );
    }

    /**
     * Add a post id to the replied post list of an author
     *
     * @param postId
     * @param author
     * @return
     */
    private AuthorEntity addPostIdToAuthorRepliedPost(String postId, AuthorEntity author) {
        if(author.posts().contains(postId)) {
            return author;
        }       
        List<String> postIds = Stream.concat(
            author.posts().stream(),
            Stream.of(postId)
        ).collect(Collectors.toList());
        return new AuthorEntity(
            author.id(),
            author.userId(),
            author.userName(),
            author.posts(),
            postIds
        );
    }
    
}
