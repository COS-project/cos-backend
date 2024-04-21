package com.cos.cercat.apis.post.dto.response;

import com.cos.cercat.apis.mockExam.dto.response.QuestionResponse;
import com.cos.cercat.apis.user.dto.response.UserResponse;
import com.cos.cercat.domain.post.*;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_NULL)
public record PostResponse(
        Long postId,
        PostContent postContent,
        Set<RecommendTag> recommendTags,
        PostStatus postStatus,
        UserResponse user,
        QuestionResponse question,
        boolean isLiked,
        DateTime dateTime
) {

    public static PostResponse of(Post post, boolean isLiked) {
        return new PostResponse(
                post.getId(),
                post.getPostContent(),
                post instanceof TipPost tipPost?
                        tipPost.getRecommendTags()
                        : null,
                post.getPostStatus(),
                UserResponse.from(post.getUser()),
                post instanceof CommentaryPost commentaryPost?
                        QuestionResponse.from(commentaryPost.getQuestion())
                        : null,
                isLiked,
                post.getDateTime()
        );
    }

}
