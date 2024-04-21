package com.cos.cercat.domain.post;

import com.cos.cercat.domain.user.User;

public record CommentaryPost(
        long id,
        User user,
        PostContent postContent,
        PostStatus postStatus
) {
}
