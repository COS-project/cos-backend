package com.cos.cercat.apis.post.dto;

import com.cos.cercat.domain.post.domain.RecommendTag;
import com.cos.cercat.domain.post.domain.TagType;

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
