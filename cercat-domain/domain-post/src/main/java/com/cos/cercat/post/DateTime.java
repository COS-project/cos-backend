package com.cos.cercat.post;

import java.time.LocalDateTime;

public record DateTime(
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
