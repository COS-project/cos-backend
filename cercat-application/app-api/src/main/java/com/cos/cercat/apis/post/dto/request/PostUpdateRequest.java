package com.cos.cercat.apis.post.dto.request;

import com.cos.cercat.domain.mockexam.MockExamSession;
import com.cos.cercat.domain.post.PostContent;
import com.cos.cercat.domain.post.RecommendTag;

import java.util.List;
import java.util.Set;

public record PostUpdateRequest(
        Long postId,
        String title,
        String content,
        Integer examYear,
        Integer round,
        Integer questionSequence,
        Set<RecommendTag> newTags,
        List<Long> removeImageIds
) {

    public PostContent toPostContent() {
        return new PostContent(title, content);
    }

    public MockExamSession toMockExamSession() {
        return new MockExamSession(examYear, round);
    }
}
