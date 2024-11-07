package com.backend.post.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PostResponseDto(

        UUID postUUID,

        UUID authorUUID,

        List<UUID> uuidsOfUsersWhoLikedThisPost,

        String postContent,

        Instant datePosted,

        Integer shareCount,

        Integer likeCount,

        List<String> tags) {
}