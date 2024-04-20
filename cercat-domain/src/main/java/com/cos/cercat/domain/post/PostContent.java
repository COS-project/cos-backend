package com.cos.cercat.domain.post;

import java.util.List;

public record PostContent(
        String title,
        String content,
        List<PostImage> images,
        PostType postType
) {
}
