package com.backend.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record PostCreationDto(


    @NotBlank(message = "post content cannot be null") @Size(max = 280) String postContent,

    Set<String> tags) {
}
