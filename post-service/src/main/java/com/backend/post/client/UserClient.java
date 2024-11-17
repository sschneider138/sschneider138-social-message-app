package com.backend.post.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;

public interface UserClient {

  Logger log = LoggerFactory.getLogger(UserClient.class);

  @GetExchange("/api/auth/validate")
  @CircuitBreaker(name = "user", fallbackMethod = "fallbackMethod")
  @Retry(name = "user")
  UserResponseDto validateUser(@RequestHeader("Authorization") String jwt);

  default void fallbackMethod(String jwt, Throwable throwable) {
    log.error("cannot validate user with token {}, failure reason {}", jwt, throwable.getMessage());
  }
}
