package com.backend.post.dto;

import java.time.Instant;
import java.util.Set;

public record PostResponseDto(

    String postUUID,

    String authorUUID,

    String authorUsername,

    Set<String> uuidsOfUsersWhoLikedThisPost,

    String postContent,

    Instant datePosted,

    Integer shareCount,

    Integer likeCount,

    Set<String> tags) {
}