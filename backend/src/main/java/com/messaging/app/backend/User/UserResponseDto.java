package com.messaging.app.backend.User;

import java.time.Instant;
import java.util.List;

public record UserResponseDto(

    String firstName,
    String lastName,
    String username,
    List<String> topInterests,
    Instant dateJoined) {
}
