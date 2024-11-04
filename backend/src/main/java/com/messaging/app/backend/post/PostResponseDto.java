package com.messaging.app.backend.post;

import com.messaging.app.backend.tags.Tag;
import com.messaging.app.backend.user.User;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public record PostResponseDto(
        User author,
        List<User> likedByUsers,
        String postContent,
        Instant datePosted,
        Integer shareCount,
        Set<Tag> tags) {
}
