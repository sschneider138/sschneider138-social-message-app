package com.messaging.app.backend.Pagination;

public record UserPaginationRequestDto(
    int pageIndex,
    int itemsPerPage) {
}
