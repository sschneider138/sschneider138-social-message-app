package com.backend.post.external.dto;

import java.time.Instant;
import java.util.List;

// used for server -> client transfer
// only include info that can be public facing
public record UserResponseDto(
    String userUUID,
    String firstName,
    String lastName,
    String username,
    String email,
    String phoneNumber,
    List<String> topInterests,
    Instant dateJoined,
    long membershipLengthInDays) {
}
