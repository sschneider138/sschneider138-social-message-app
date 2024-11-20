package com.backend.post.external.client;

import com.backend.post.external.dto.LikeDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface LikeClient {

  Logger log = LoggerFactory.getLogger(LikeClient.class);

  @PostExchange("/api/like/like-post")
  @CircuitBreaker(name = "like", fallbackMethod = "fallbackMethod")
  @Retry(name = "like")
  String toggleLike(@Valid @RequestBody LikeDto likeDto);

  default String fallbackMethod(@Valid @RequestBody LikeDto likeDto, Throwable throwable) {
    log.error("cannot like post: {}\nfrom user: {}\nfailure reason: {}", likeDto.postUUID(), likeDto.userUUID(), throwable.getMessage());
    return "fallback_triggered";
  }
}
