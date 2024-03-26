package com.cos.cercat.apis.user.dto.request;

public record SearchLogDeleteRequest(
        String keyword,
        String createdAt
) {
}
