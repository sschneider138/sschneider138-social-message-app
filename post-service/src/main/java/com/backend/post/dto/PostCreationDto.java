package com.backend.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record PostCreationDto(

        @NotNull(message = "user required for a post") UUID authorUUID,

        @NotBlank(message = "post content cannot be null")
        @Size(max = 280)
        String postContent,

        List<String> tags
) {
}
