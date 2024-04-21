package com.cos.cercat.apis.post.dto;


import com.cos.cercat.domain.post.RecommendTag;
import com.cos.cercat.domain.post.RecommendTagEntity;
import com.cos.cercat.domain.post.TagType;

public record RecommendTagDTO(
        TagType tagType,
        String tagName
) {

    public RecommendTagEntity toEntity() {
        return new RecommendTagEntity(
                tagType,
                tagName
        );
    }

    public static RecommendTagDTO from(RecommendTagEntity entity) {
        return new RecommendTagDTO(
                entity.getTagType(),
                entity.getTagName()
        );
    }

    public static RecommendTagDTO from(RecommendTag recommendTag) {
        return new RecommendTagDTO(
                recommendTag.tagType(),
                recommendTag.tagName()
        );
    }

}
