package com.backend.post.client;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;

public interface UserClient {

  @GetExchange("/api/auth/validate")
  UserResponseDto validateUser(@RequestHeader("Authorization") String jwt);

}
