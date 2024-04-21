package com.cos.cercat.apis.comment.dto.request;


import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.comment.PostCommentEntity;
import com.cos.cercat.domain.post.PostEntity;

public record PostCommentCreateRequest(
        Long parentCommentId,
        String content
) {
    public PostCommentEntity toEntity(PostEntity postEntity, UserEntity userEntity) {
        return PostCommentEntity.of(
                userEntity,
                postEntity,
                content
        );
    }
}
