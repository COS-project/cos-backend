package com.cos.cercat.apis.post.request;


import com.cos.cercat.mockexam.MockExamSession;
import com.cos.cercat.post.*;

import java.util.List;
import java.util.Set;

public record PostUpdateRequest(
        Long postId,
        String title,
        String content,
        Integer examYear,
        Integer round,
        Integer questionSequence,
        Set<RecommendTag> tags,
        List<Long> removeImageIds
) {

    public UpdatedPost toUpdatedPost(PostType postType) {
        return new UpdatedPost(
                postType,
                new PostContent(title, content),
                new MockExamSession(examYear, round),
                questionSequence,
                tags,
                removeImageIds
        );
    }
}
