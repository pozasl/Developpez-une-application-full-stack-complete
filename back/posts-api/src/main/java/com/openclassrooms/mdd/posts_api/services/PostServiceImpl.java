package com.openclassrooms.mdd.posts_api.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mdd.common.exception.ResourceNotFoundException;
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
        return postRepository.findById(id).switchIfEmpty(Mono.error(new ResourceNotFoundException("No post with id " + id)));
    }

    /**
     * Create a new Post and add it to the Author's post list
     */
    @Override
    public Mono<PostEntity> create(PostEntity postEntity) {

        return postRepository.save(postEntity)
            .flatMap(post -> {
                System.out.println(post.toString());
                return authorRepository.findByUserId(post.author().userId())
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
            .switchIfEmpty(Mono.error(new ResourceNotFoundException("Nos post found with id " + id)))
            .flatMap(p -> {
                return postRepository.addReplyToPostId(id, reply).then(postRepository.findById(id));
            })
            .flatMap(post -> {
                System.out.println(post.toString());
                return authorRepository.findByUserId(post.author().userId())
                .switchIfEmpty(authorRepository.save(post.author()))
                .flatMap(author -> {
                    System.out.println("1 -> " + author.toString());
                    AuthorEntity updatedAuthor = addPostIdToAuthorRepliedPost(post.id(), author);
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
        if(author.replies().contains(postId)) {
            return author;
        }       
        List<String> postIds = Stream.concat(
            author.replies().stream(),
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
