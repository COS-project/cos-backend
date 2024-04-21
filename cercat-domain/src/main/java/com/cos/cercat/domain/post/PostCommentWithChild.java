package com.cos.cercat.domain.post;

import java.util.List;

public record PostCommentWithChild(
        PostComment postComment,
        List<PostComment> childComments
) {
}
