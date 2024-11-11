package com.backend.post.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "user", url = "http://localhost:8080")
public interface UserClient {

    @GetMapping("/api/user/validate")
    boolean isUserAuthenticated(@RequestHeader("Authorization") String jwt);

    @GetMapping("/api/user/get/{uuid}")
    UserResponseDto getIndividualUserInfo(@PathVariable String userUUID);
}
