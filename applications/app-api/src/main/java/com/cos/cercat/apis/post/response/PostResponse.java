package com.cos.cercat.apis.post.response;

import com.cos.cercat.apis.mockExam.response.QuestionResponse;
import com.cos.cercat.apis.user.response.UserResponse;
import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.like.LikeStatus;
import com.cos.cercat.domain.post.*;

import com.fasterxml.jackson.annotation.JsonInclude;


import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_NULL)
public record PostResponse(
        Long postId,
        PostContent postContent,
        PostType postType,
        UserResponse user,
        QuestionResponse question,
        Set<RecommendTag> recommendTags,
        List<Image> postImages,
        DateTime dateTime,
        int commentCount,
        long likeCount,
        boolean likeStatus
) {

    public static PostResponse from(Post post, LikeStatus likeStatus) {
        return new PostResponse(
                post.getId(),
                post.getPostContent(),
                post.getType(),
                UserResponse.from(post.getWriter()),
                post instanceof CommentaryPost commentaryPost?
                        com.cos.cercat.apis.mockExam.response.QuestionResponse.from(commentaryPost.getQuestion())
                        : null,
                post instanceof TipPost tipPost?
                        tipPost.getRecommendTags()
                        : null,
                post.getPostImages(),
                post.getDateTime(),
                post.getCommentCount(),
                likeStatus.count().value(),
                likeStatus.isLiked()
        );
    }

}
