package com.backend.post.dto;

import jakarta.validation.constraints.NotBlank;

public record PostContentUpdateDto(
        @NotBlank(message = "user uuid mist be provided") String userUUID,

        @NotBlank(message = "post uuid must be provided") String postUUID,

        @NotBlank(message = "post content cannot be empty") String postContent) {
}
