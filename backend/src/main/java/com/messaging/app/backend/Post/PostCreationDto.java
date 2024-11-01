package com.messaging.app.backend.Post;

import com.messaging.app.backend.Tags.Tag;
import com.messaging.app.backend.User.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record PostCreationDto(
        @NotNull(message = "user required for a post") User author,

        @NotBlank(message = "post content cannot be null") @Size(max = 280) String postContent,

        Set<Tag> tags) {
}
