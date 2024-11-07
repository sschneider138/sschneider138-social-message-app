package com.backend.post.dto;

public record PaginationRequestDto(
        int pageIndex,
        int itemsPerPage) {
}
