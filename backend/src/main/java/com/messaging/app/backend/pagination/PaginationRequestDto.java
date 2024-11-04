package com.messaging.app.backend.pagination;

public record PaginationRequestDto(
        int pageIndex,
        int itemsPerPage) {
}
