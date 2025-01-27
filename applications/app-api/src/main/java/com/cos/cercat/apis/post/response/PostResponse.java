package com.cos.cercat.apis.post.response;

import com.cos.cercat.apis.mockExam.response.QuestionResponse;
import com.cos.cercat.apis.user.response.UserResponse;
import com.cos.cercat.domain.post.*;

import com.fasterxml.jackson.annotation.JsonInclude;


import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_NULL)
public record PostResponse(
        Long postId,
        PostContent postContent,
        UserResponse user,
        QuestionResponse question,
        Set<RecommendTag> recommendTags,
        DateTime dateTime,
        int commentCount
) {

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getPostContent(),
                UserResponse.from(post.getWriter()),
                post instanceof CommentaryPost commentaryPost?
                        QuestionResponse.from(commentaryPost.getQuestion())
                        : null,
                post instanceof TipPost tipPost?
                        tipPost.getRecommendTags()
                        : null,
                post.getDateTime(),
                post.getCommentCount()
        );
    }

}
