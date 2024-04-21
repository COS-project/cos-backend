package com.cos.cercat.domain.post;

import com.cos.cercat.domain.user.User;

import java.util.List;

public record PostComment(
        Long id,
        User user,
        String content,
        Long parentId,
        int likeCount,
        DateTime dateTime,
        List<PostComment> childComments
) {
    public void addChildren(PostComment children) {
        childComments.add(children);
    }

    public boolean hasParent() {
        return parentId != null;
    }
}
