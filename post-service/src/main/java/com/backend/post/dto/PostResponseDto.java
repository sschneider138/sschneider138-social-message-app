package com.backend.post.dto;

import java.time.Instant;
import java.util.List;

public record PostResponseDto(

        String postUUID,

        String authorUUID,

        List<String> uuidsOfUsersWhoLikedThisPost,

        String postContent,

        Instant datePosted,

        Integer shareCount,

        Integer likeCount,

        List<String> tags) {
}