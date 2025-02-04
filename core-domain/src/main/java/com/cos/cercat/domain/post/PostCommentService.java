package com.cos.cercat.domain.post;

import com.cos.cercat.domain.user.PermissionValidator;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostReader postReader;
    private final PostCommentAppender postCommentAppender;
    private final UserReader userReader;
    private final PostCommentReader postCommentReader;
    private final PostRemover postRemover;
    private final PermissionValidator permissionValidator;

    public void deletePostComment(TargetUser targetUser, TargetComment targetComment) {
        User user = userReader.read(targetUser);
        PostComment postComment = postCommentReader.read(targetComment);
        permissionValidator.validate(postComment, user);
        postRemover.remove(postComment);
    }

    public void createPostComment(
            TargetUser targetUser,
            TargetPost targetPost,
            CommentContent commentContent
    ) {
        User user = userReader.read(targetUser);
        Post post = postReader.read(targetPost);
        postCommentAppender.append(user, post, commentContent);
    }
}
