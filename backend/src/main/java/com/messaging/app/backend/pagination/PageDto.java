package com.messaging.app.backend.pagination;

import java.util.List;

public record PageDto<T>(
    List<T> content,
    int totalPages,
    int totalElements,
    int pageIndex,
    int itemsPerPage) {
}
