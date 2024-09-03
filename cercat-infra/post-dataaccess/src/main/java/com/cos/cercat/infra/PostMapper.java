package com.cos.cercat.infra;

import com.cos.cercat.infra.entity.PostEntity;
import com.cos.cercat.infra.entity.PostImageEntity;
import com.cos.cercat.infra.entity.RecommendTagEntity;
import com.cos.cercat.infra.entity.TipPostEntity;
import com.cos.cercat.infra.entity.ImageEntity;
import com.cos.cercat.domain.post.PostContent;
import com.cos.cercat.domain.post.RecommendTag;

import java.util.List;
import java.util.Set;

public class PostMapper {

    public static List<PostImageEntity> toPostImageEntities(PostContent postContent, PostEntity savedPost) {
        return postContent.getImages().stream()
                .map(image -> PostImageEntity.of(savedPost, ImageEntity.from(image)))
                .toList();
    }

    public static List<RecommendTagEntity> toRecommendTagEntities(Set<RecommendTag> recommendTags, TipPostEntity savedPost) {
        return recommendTags.stream()
                .map(recommendTag -> RecommendTagEntity.of(savedPost, recommendTag))
                .toList();
    }
}
