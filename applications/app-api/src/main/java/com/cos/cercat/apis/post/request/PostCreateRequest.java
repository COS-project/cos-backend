package com.cos.cercat.apis.post.request;


import com.cos.cercat.domain.mockexam.MockExamSession;
import com.cos.cercat.domain.post.NewPost;
import com.cos.cercat.domain.post.PostContent;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.post.RecommendTag;

import java.util.Set;

public record PostCreateRequest(
        String title,
        String content,
        Set<RecommendTag> tags,
        Integer examYear,
        Integer round,
        Integer questionSequence
) {
    public NewPost toNewPost(PostType postType) {
        return new NewPost(
                postType,
                new PostContent(title, content),
                new MockExamSession(examYear, round),
                questionSequence,
                tags
        );
    }
}
