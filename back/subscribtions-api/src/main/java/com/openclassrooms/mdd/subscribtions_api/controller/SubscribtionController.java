package com.openclassrooms.mdd.subscribtions_api.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mdd.api.SubscribtionsApiDelegate;
import com.openclassrooms.mdd.api.model.ResponseMessage;
import com.openclassrooms.mdd.api.model.Topic;
import com.openclassrooms.mdd.subscribtions_api.service.SubscribtionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@SecurityRequirement(name = "Authorization")
public class SubscribtionController implements SubscribtionsApiDelegate{
    
    private SubscribtionService subService;

    SubscribtionController(SubscribtionService subService) {
        this.subService = subService;
    }

    @PostMapping("/api/subscriptions/user/{userid}/topics/{ref}")
    Mono<ResponseMessage> subscribeToTopic(@PathVariable Long userid, @PathVariable String ref, @AuthenticationPrincipal Jwt jwt) {
        if (!jwt.getClaim("userId").equals(userid)) {
            return Mono.error(new AccessDeniedException("Can't subscribe another user"));
        }
        return subService.subscribeUserToTopic(userid, ref).then(Mono.just(new ResponseMessage().message("Subscribtion succeeded")));
    }

    @DeleteMapping("/api/subscriptions/user/{userid}/topics/{ref}")
    Mono<ResponseMessage> unsubscribe(@PathVariable Long userid, @PathVariable String ref, @AuthenticationPrincipal Jwt jwt) {
        if (!jwt.getClaim("userId").equals(userid)) {
            return Mono.error(new AccessDeniedException("Can't unsubscribe another user"));
        }
        return subService.unsubscribeUserToTopic(userid, ref).then(Mono.just(new ResponseMessage().message("Subscribtion removed")));
    }

    @GetMapping("/api/subscribtions/user/{userid}/topics")
    Flux<Topic> getUserSubscribtions(@PathVariable Long userid, @AuthenticationPrincipal Jwt jwt) {
        if (!jwt.getClaim("userId").equals(userid)) {
            return Flux.error(new AccessDeniedException("Can't see another user subs"));
        }
        return subService.findSubsByUserId(userid).map(sub -> new Topic().ref(sub.topicRef()));
    }
}
