package com.messaging.app.backend.Post;

import com.messaging.app.backend.Tags.Tag;
import com.messaging.app.backend.User.User;

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
