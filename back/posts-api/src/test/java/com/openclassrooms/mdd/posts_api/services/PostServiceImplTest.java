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
import com.openclassrooms.mdd.posts_api.repository.PostRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private PostEntity post;

    private String postId = "12345678909876543210abcdef";

    @BeforeEach
    void setup() {
        AuthorEntity bob = new AuthorEntity(1L, "Bob");
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

        when(postRepository.save(post)).thenReturn(Mono.just(post));
        postService.create(post)
            .as(StepVerifier::create)
            .consumeNextWith(p -> {
                assertThat(p).isEqualTo(post);
                verify(postRepository).save(post);
            })
            .verifyComplete();
    }
}
