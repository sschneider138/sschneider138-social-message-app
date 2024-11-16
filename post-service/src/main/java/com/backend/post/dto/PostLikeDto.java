package com.backend.post.dto;

import jakarta.validation.constraints.NotBlank;

public record PostLikeDto(
    @NotBlank(message = "user uuid must be provided") String userUUID,

    @NotBlank(message = "username must be provided") String username,

    @NotBlank(message = "post uuid must be provided") String postUUID) {
}
