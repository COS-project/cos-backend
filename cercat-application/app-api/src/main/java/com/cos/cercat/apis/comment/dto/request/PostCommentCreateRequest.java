package com.cos.cercat.apis.comment.dto.request;


import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.comment.PostComment;
import com.cos.cercat.domain.post.PostEntity;

public record PostCommentCreateRequest(
        Long parentCommentId,
        String content
) {
    public PostComment toEntity(PostEntity postEntity, UserEntity userEntity) {
        return PostComment.of(
                userEntity,
                postEntity,
                content
        );
    }
}
