package com.openclassrooms.mdd.feeds_api.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mdd.api.FeedsApiDelegate;
import com.openclassrooms.mdd.api.model.Post;
import com.openclassrooms.mdd.feeds_api.service.FeedService;
import com.openclassrooms.mdd.feeds_api.service.FeedServiceImpl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import reactor.core.publisher.Flux;

@RestController
@SecurityRequirement(name = "Authorization")
public class FeedController implements FeedsApiDelegate{

    private FeedService feedService;

    FeedController(FeedServiceImpl feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/api/feeds/{userid}")
    Flux<Post> getUserFeed(@PathVariable Long userid, @AuthenticationPrincipal Jwt jwt) {
        if (!jwt.getClaim("userId").equals(userid))
        {
            return Flux.error(new AccessDeniedException("Unauthorized access"));
        }
        return feedService.findPostByUserId(userid).map(feed -> new Post().id(feed.getPostRef()));
    }
    
}
