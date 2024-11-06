package com.backend.user.dto;

public record PaginationRequestDto(
        int pageIndex,
        int itemsPerPage) {
}
