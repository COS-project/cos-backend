package com.cos.cercat.post;

import com.cos.cercat.user.PermissionValidator;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePostService {

    private final UserReader userReader;
    private final PostReader postReader;
    private final CommentReader commentReader;
    private final PostRemover postRemover;
    private final PermissionValidator permissionValidator;

    public void deletePost(TargetUser targetUser, TargetPost targetPost) {
        User user = userReader.read(targetUser);
        Post post = postReader.read(targetPost);
        permissionValidator.validate(post, user);
        postRemover.remove(post);
    }

    public void deletePostComment(TargetUser targetUser, TargetComment targetComment) {
        User user = userReader.read(targetUser);
        PostComment postComment = commentReader.read(targetComment);
        permissionValidator.validate(postComment, user);
        postRemover.remove(postComment);
    }
}
