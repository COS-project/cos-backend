package com.cos.cercat.database.post;

import com.cos.cercat.database.common.entity.ImageEntity;
import com.cos.cercat.database.post.entity.PostEntity;
import com.cos.cercat.database.post.entity.PostImageEntity;
import com.cos.cercat.database.post.entity.RecommendTagEntity;
import com.cos.cercat.database.post.entity.TipPostEntity;
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
