package com.cos.cercat.comment.app;

import com.cos.cercat.alarm.app.producer.AlarmProducer;
import com.cos.cercat.alarm.domain.AlarmType;
import com.cos.cercat.alarm.dto.AlarmArg;
import com.cos.cercat.alarm.dto.AlarmEvent;
import com.cos.cercat.board.app.PostService;
import com.cos.cercat.board.domain.Post;
import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.comment.dto.request.PostCommentCreateRequest;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostCommentCreateService {

    private final PostCommentService postCommentService;
    private final UserService userService;
    private final PostService postService;
    private final AlarmProducer alarmProducer;

    @Transactional
    public void createPostComment(Long postId, PostCommentCreateRequest request, Long userId) {
        Post post = postService.getPost(postId);
        User user = userService.getUser(userId);

        PostComment postComment = request.toEntity(post, user);

        if (request.parentCommentId() != null) {
            PostComment parentComment = postCommentService.getPostComment(request.parentCommentId());
            parentComment.addChildComment(postComment);
            return;
        }

        post.addComment(postComment);
        alarmProducer.send(createAlarmEvent(post, user));
    }

    private AlarmEvent createAlarmEvent(Post targetPost, User fromUser) {
        return AlarmEvent.of(
                targetPost.getUser(),
                AlarmArg.of(fromUser, targetPost.getId()),
                AlarmType.NEW_COMMENT_ON_POST
        );
    }

}
