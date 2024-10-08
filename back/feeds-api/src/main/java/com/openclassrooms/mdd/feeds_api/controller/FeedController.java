package com.openclassrooms.mdd.feeds_api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.openclassrooms.mdd.api.FeedsApiDelegate;
import com.openclassrooms.mdd.api.model.Post;
import com.openclassrooms.mdd.feeds_api.service.FeedService;
import com.openclassrooms.mdd.feeds_api.service.FeedServiceImpl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@SecurityRequirement(name = "Authorization")
public class FeedController implements FeedsApiDelegate {

    private FeedService feedService;
    private WebClient webClient;

    @Value("${service.posts.base-url}")
    private String postServiceUrl;

    FeedController(FeedServiceImpl feedService, WebClient webClient) {
        this.feedService = feedService;
        this.webClient = webClient;
    }

    @GetMapping("/api/feeds/{userid}")
    Flux<Post> getUserFeed(
            @PathVariable Long userid,
            @AuthenticationPrincipal Jwt jwt,
            @RequestHeader Map<String, String> headers) {
        if (!jwt.getClaim("userId").equals(userid)) {
            return Flux.error(new AccessDeniedException("Unauthorized access"));
        }
        // Sometimes headers key are converted to lower case by proxy
        String tokenBearer =  (headers.get("Authorization") != null) ? headers.get("Authorization") : headers.get("authorization");
        return feedService.findPostByUserId(userid)
                .flatMapSequential(feedPost -> fetchPost(feedPost.getPostRef(), tokenBearer));
    }

    private Mono<Post> fetchPost(String postRef, String token) {
        String url = postServiceUrl + "/" + postRef;
        return webClient.get().uri(url)
                .header("Authorization", token)
                .retrieve().bodyToMono(Post.class);
    }

}
