package com.cos.cercat.domain.post;

import java.util.List;

public record PostContent(
        String title,
        String content,
        PostType postType,
        List<String> imageUrls
) {

    public String getThumbnail() {
        if (imageUrls.isEmpty()) {
            return "";
        }
        return imageUrls.get(0);
    }
}
