package com.messaging.app.backend.user;

import java.time.Instant;
import java.util.List;

// used for server -> client transfer
public record UserResponseDto(

        String firstName,
        String lastName,
        String username,
        String email,
        String phoneNumber,
        List<String> topInterests,
        Instant dateJoined) {
}
