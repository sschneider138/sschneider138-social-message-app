package com.backend.post.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "user", url = "http://localhost:8080")
public interface UserClient {

  @GetMapping("/api/auth/validate")
  UserResponseDto validateUser(@RequestHeader("Authorization") String jwt);

}
