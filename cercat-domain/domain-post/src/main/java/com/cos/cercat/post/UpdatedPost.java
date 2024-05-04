package com.cos.cercat.post;

import com.cos.cercat.mockexam.MockExamSession;

import java.util.List;
import java.util.Set;

public record UpdatedPost(
        PostType postType,
        PostContent content,
        MockExamSession mockExamSession,
        Integer questionSequence,
        Set<RecommendTag> tags,
        List<Long> removeImageIds
) {
}
