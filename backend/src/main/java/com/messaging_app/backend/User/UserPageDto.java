package com.messaging_app.backend.User;

import java.util.List;

public record UserPageDto(
    List<UserResponseDto> users,
    int totalPages,
    int totalElements,
    int pageIndex,
    int itemsPerPage) {
}
