package com.cos.cercat.domain.post;

import java.time.LocalDateTime;

public record DateTime(
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
