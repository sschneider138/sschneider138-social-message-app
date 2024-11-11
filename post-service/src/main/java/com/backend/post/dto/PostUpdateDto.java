package com.backend.post.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record PostUpdateDto(

        @NotNull(message = "post uuid must be provided") String postUUID,

        @NotNull(message = "post content cannot be empty") String postContent,

        List<String> uuidsOfUsersWhoLikedThisPost) {

}
