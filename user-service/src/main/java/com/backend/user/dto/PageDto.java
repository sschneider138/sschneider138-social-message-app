package com.backend.user.dto;

import java.util.List;

public record PageDto<T>(
        List<T> items,
        int totalPages,
        int totalElements,
        int pageIndex,
        int itemsPerPage) {
}
