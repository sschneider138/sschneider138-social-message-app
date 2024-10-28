package com.messaging_app.backend.User;

import java.time.LocalDate;
import java.util.List;

public record UserResponseDto(

    String firstName,
    String lastName,
    String username,
    List<String> topInterests,
    LocalDate dateJoined) {

}
