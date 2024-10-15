package com.openclassrooms.mdd.subscribtions_api.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.openclassrooms.mdd.api.SubscribtionsApiDelegate;
import com.openclassrooms.mdd.api.model.ResponseMessage;
import com.openclassrooms.mdd.api.model.Topic;
import com.openclassrooms.mdd.subscribtions_api.service.SubscribtionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Subscribtions API controller
 */
@RestController
@SecurityRequirement(name = "Authorization")
public class SubscribtionController implements SubscribtionsApiDelegate{
    
    private SubscribtionService subService;
    private WebClient webClient;

    @Value("${service.topics.base-url}")
    private String topicServiceUrl;

    SubscribtionController(SubscribtionService subService,  WebClient webClient) {
        this.subService = subService;
        this.webClient = webClient;
    }

    /**
     * Subscribe to Topic
     *
     * @param userid Subscriber user id
     * @param ref Topic's ref
     * @param jwt Jwt authentication
     * @return Response message
     */
    @PostMapping("/api/subscribtions/user/{userid}/topics/{ref}")
    Mono<ResponseMessage> subscribeToTopic(@PathVariable Long userid, @PathVariable String ref, @AuthenticationPrincipal Jwt jwt) {
        if (!jwt.getClaim("userId").equals(userid)) {
            return Mono.error(new AccessDeniedException("Can't subscribe another user"));
        }
        return subService.subscribeUserToTopic(userid, ref).then(Mono.just(new ResponseMessage().message("Subscribtion succeeded")));
    }

    /**
     * Unsubscribe to Topic
     *
     * @param userid Subscriber user id
     * @param ref Topic's ref
     * @param jwt Jwt authentication
     * @return Response message
     */
    @DeleteMapping("/api/subscribtions/user/{userid}/topics/{ref}")
    Mono<ResponseMessage> unsubscribe(@PathVariable Long userid, @PathVariable String ref, @AuthenticationPrincipal Jwt jwt) {
        if (!jwt.getClaim("userId").equals(userid)) {
            return Mono.error(new AccessDeniedException("Can't unsubscribe another user"));
        }
        return subService.unsubscribeUserToTopic(userid, ref).then(Mono.just(new ResponseMessage().message("Subscribtion removed")));
    }

    /**
     * Get a user's subscribed topics
     *
     * @param userid the user id
     * @param jwt Jwt authentication
     * @param headers request headers
     * @return
     */
    @GetMapping("/api/subscribtions/user/{userid}/topics")
    Flux<Topic> getUserSubscribtions(@PathVariable Long userid,
        @AuthenticationPrincipal Jwt jwt,
        @RequestHeader Map<String, String> headers) {
        // Sometimes headers key are converted to lower case by proxy
        String tokenBearer =  (headers.get("Authorization") != null) ? headers.get("Authorization") : headers.get("authorization");
        if (!jwt.getClaim("userId").equals(userid)) {
            return Flux.error(new AccessDeniedException("Can't see another user subs"));
        }
        return subService.findSubsByUserId(userid)
            .flatMapSequential(sub -> fetchTopic(sub.topicRef(), tokenBearer));
    }

    /**
     * Fetch a topic data by its ref with Topics API
     *
     * @param topicRef the topic's ref
     * @param token jwt token
     * @return a Topic
     */
    private Mono<Topic> fetchTopic(String topicRef, String token) {
        String url = topicServiceUrl + "/" + topicRef;
        return webClient.get().uri(url)
            .header("Authorization", token)
            .retrieve().bodyToMono(Topic.class);
    }
}
