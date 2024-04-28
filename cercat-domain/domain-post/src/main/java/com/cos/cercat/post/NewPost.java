package com.cos.cercat.post;

import com.cos.cercat.mockexam.MockExamSession;

import java.util.Set;

public record NewPost(
        PostType postType,
        PostContent content,
        MockExamSession mockExamSession,
        Integer questionSequence,
        Set<RecommendTag> tags
) {
}
