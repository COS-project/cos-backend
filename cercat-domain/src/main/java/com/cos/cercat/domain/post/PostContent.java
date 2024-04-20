package com.cos.cercat.domain.post;

import java.util.List;

public record PostContent(
        String title,
        String content,
        PostType postType,
        List<String> imageUrls
) {
}
