package com.messaging.app.backend.Pagination;

public record PaginationRequestDto(
        int pageIndex,
        int itemsPerPage) {
}
