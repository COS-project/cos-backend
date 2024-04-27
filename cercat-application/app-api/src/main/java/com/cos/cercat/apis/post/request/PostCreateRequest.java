package com.cos.cercat.apis.post.request;


import com.cos.cercat.mockexam.MockExamSession;
import com.cos.cercat.post.PostContent;
import com.cos.cercat.post.RecommendTag;

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
