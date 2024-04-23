package com.cos.cercat.apis.user.dto.request;

import com.cos.cercat.domain.search.SearchLog;

import java.time.LocalDateTime;

public record SearchLogDeleteRequest(
        String keyword,
        LocalDateTime createdAt
) {
    public SearchLog toSearchLog() {
        return new SearchLog(
                keyword,
                createdAt.toString()
        );
    }
}
