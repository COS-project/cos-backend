package com.cos.cercat.apis.post.dto.request;

import com.cos.cercat.apis.post.dto.RecommendTagDTO;

import java.util.List;
import java.util.Set;

public record PostUpdateRequest(
        Long postId,
        String title,
        String content,
        Integer examYear,
        Integer round,
        Integer questionSequence,
        Set<RecommendTagDTO> newTags,
        List<String> removeImageUrls
) {
}
