package com.cos.cercat;

import com.cos.cercat.domain.PostEntity;
import com.cos.cercat.domain.PostImageEntity;
import com.cos.cercat.domain.RecommendTagEntity;
import com.cos.cercat.domain.TipPostEntity;
import com.cos.cercat.entity.ImageEntity;
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
