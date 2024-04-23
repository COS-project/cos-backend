package com.cos.cercat.apis.post.dto.request;

import com.cos.cercat.common.domain.Image;
import com.cos.cercat.domain.mockexam.MockExamSession;
import com.cos.cercat.domain.post.*;

import java.util.List;
import java.util.Set;

public record PostCreateRequest(
        String title,
        String content,
        Set<RecommendTag> tags,
        Integer examYear,
        Integer round,
        Integer questionSequence
) {

    public PostContent toContent() {
        return new PostContent(title, content);
    }

    public MockExamSession toMockExamSession() {
        return new MockExamSession(examYear, round);
    }
}
