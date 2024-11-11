package com.backend.post.dto;

import jakarta.validation.constraints.NotBlank;

public record PostLikeDto(
        @NotBlank(message = "user uuid mist be provided") String userUUID,

        @NotBlank(message = "post uuid must be provided") String postUUID) {
}
