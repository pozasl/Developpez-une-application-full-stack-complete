package com.openclassrooms.mdd.posts_api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.openclassrooms.mdd.posts_api.model.AuthorEntity;
import com.openclassrooms.mdd.posts_api.model.PostEntity;
import com.openclassrooms.mdd.posts_api.repository.AuthorRepository;
import com.openclassrooms.mdd.posts_api.repository.PostRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private PostEntity post;

    private String postId = "12345678909876543210abcdef";

    private AuthorEntity bob;

    @BeforeEach
    void setup() {
        bob = new AuthorEntity("123456789098765432100001", 1L, "Bob", List.of(), List.of());
        post = new PostEntity(postId, "Hello world", "Hello world bla bla bla", new Date(), bob, "java", List.of());
    }

    @Test
    void postIdExists_getPostById_shouldReturnMonoPost() {
        when(postRepository.findById(postId)).thenReturn(Mono.just(post));
        postService.getPostById(postId)
            .as(StepVerifier::create)
            .consumeNextWith(p -> {
                assertThat(p).isEqualTo(post);
            })
            .verifyComplete();
    }

    @Test
    void postIdNotExist_getPostById_shouldReturnMonoError() {
        when(postRepository.findById(postId)).thenReturn(Mono.empty());
        postService.getPostById(postId)
            .as(StepVerifier::create)
            .consumeErrorWith(e -> {
                assertThat(e).isInstanceOf(NotFoundException.class);
            });
    }

    @Test
    void withNewPost_create_shouldSavePost() {
        AuthorEntity bobWithPost = new AuthorEntity("123456789098765432100001", 1L, "Bob", List.of(post.id()), List.of());
        when(postRepository.save(post)).thenReturn(Mono.just(post));
        when(authorRepository.findById("123456789098765432100001")).thenReturn(Mono.just(bob));
        when(authorRepository.save(bob)).thenReturn(Mono.just(bob));
        when(authorRepository.save(bobWithPost)).thenReturn(Mono.just(bobWithPost));
        postService.create(post)
            .as(StepVerifier::create)
            .consumeNextWith(p -> {
                assertThat(p).isEqualTo(post);
                verify(postRepository).save(post);
            })
            .verifyComplete();
    }

    @Test
    void withNewPostAndNewAuthor_create_shouldSavePostAndAddItsIdtoAuthorPosts() {
        AuthorEntity bobWithPost = new AuthorEntity("123456789098765432100001", 1L, "Bob", List.of(post.id()), List.of());
        when(postRepository.save(post)).thenReturn(Mono.just(post));
        when(authorRepository.findById("123456789098765432100001")).thenReturn(Mono.empty());
        when(authorRepository.save(bob)).thenReturn(Mono.just(bob));
        when(authorRepository.save(bobWithPost)).thenReturn(Mono.just(bobWithPost));
        postService.create(post)
            .as(StepVerifier::create)
            .consumeNextWith(p -> {
                assertThat(p).isEqualTo(post);
                verify(postRepository).save(post);
            })
            .verifyComplete();
    }
}
