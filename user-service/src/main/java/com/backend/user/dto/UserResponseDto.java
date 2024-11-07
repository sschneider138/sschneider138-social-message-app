package com.backend.user.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

// used for server -> client transfer
// only include info that can be public facing
public record UserResponseDto(
        UUID userUUID,
        String firstName,
        String lastName,
        String username,
        String email,
        String phoneNumber,
        List<String> topInterests,
        Instant dateJoined,
        long membershipLengthInDays
) {
}