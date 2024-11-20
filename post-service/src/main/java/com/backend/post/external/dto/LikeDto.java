package com.backend.post.external.dto;

import jakarta.validation.constraints.NotNull;

public record LikeDto(
    @NotNull(message = "post uuid cannot be null")
    String postUUID,

    @NotNull(message = "user uuid cannot be null")
    String userUUID
) {
}
