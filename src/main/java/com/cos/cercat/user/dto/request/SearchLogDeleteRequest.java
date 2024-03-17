package com.cos.cercat.user.dto.request;

public record SearchLogDeleteRequest(
        String keyword,
        String createdAt
) {
}
