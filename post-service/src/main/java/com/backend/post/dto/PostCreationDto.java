package com.backend.post.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCreationDto(

        @NotNull(message = "user required for a post") String authorUUID,

        @NotBlank(message = "post content cannot be null") @Size(max = 280) String postContent,

        Set<String> tags) {
}
