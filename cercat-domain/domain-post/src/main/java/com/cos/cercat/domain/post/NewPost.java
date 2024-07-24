package com.cos.cercat.domain.post;

import com.cos.cercat.domain.mockexam.MockExamSession;

import java.util.Set;

public record NewPost(
        PostType postType,
        PostContent content,
        MockExamSession mockExamSession,
        Integer questionSequence,
        Set<RecommendTag> tags
) {
}
