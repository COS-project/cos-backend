package com.cos.cercat.post.dto;

import com.cos.cercat.post.domain.RecommendTag;
import com.cos.cercat.post.domain.TagType;

public record RecommendTagDTO(
        TagType tagType,
        String tagName
) {

    public RecommendTag toEntity() {
        return new RecommendTag(
                tagType,
                tagName
        );
    }

    public static RecommendTagDTO from(RecommendTag entity) {
        return new RecommendTagDTO(
                entity.getTagType(),
                entity.getTagName()
        );
    }

}
