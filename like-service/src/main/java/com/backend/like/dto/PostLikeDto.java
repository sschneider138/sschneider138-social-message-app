package com.backend.like.dto;

import jakarta.validation.constraints.NotBlank;

public record PostLikeDto(
    @NotBlank(message = "post uuid cannot be blank") String postUUID,
    @NotBlank(message = "user uuid cannot be blank") String userUUID
) {
}
