package com.cos.cercat.domain.post;

import com.cos.cercat.domain.user.PermissionValidator;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostReader postReader;
    private final PostCommentAppender postCommentAppender;
    private final UserReader userReader;
    private final PostCommentReader postCommentReader;
    private final PostRemover postRemover;
    private final PermissionValidator permissionValidator;

    @Transactional
    public void deletePostComment(UserId userId, CommentId commentId) {
        User user = userReader.read(userId);
        PostComment postComment = postCommentReader.read(commentId);
        permissionValidator.validate(postComment, user);
        postRemover.remove(postComment);
    }

    public void createPostComment(
            UserId userId,
            PostId postId,
            CommentContent commentContent
    ) {
        User user = userReader.read(userId);
        Post post = postReader.read(postId);
        postCommentAppender.append(user, post, commentContent);
    }
}
