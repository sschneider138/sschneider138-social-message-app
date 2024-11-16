package com.backend.post.dto;

import java.time.Instant;
import java.util.Set;

public record PostResponseDto(

    String postUUID,

    String authorUUID,

    String authorUsername,

    String postContent,

    Instant datePosted,

    Integer shareCount,

    Set<String> tags) {
}