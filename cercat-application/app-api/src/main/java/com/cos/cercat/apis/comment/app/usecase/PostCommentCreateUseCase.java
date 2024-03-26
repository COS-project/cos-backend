package com.cos.cercat.apis.comment.app.usecase;

import com.cos.cercat.apis.alarm.app.kafka.producer.AlarmProducer;
import com.cos.cercat.apis.comment.dto.request.PostCommentCreateRequest;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.domain.AlarmType;
import com.cos.cercat.domain.User;
import com.cos.cercat.domain.comment.PostComment;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.dto.AlarmArg;
import com.cos.cercat.dto.AlarmEvent;
import com.cos.cercat.service.UserService;
import com.cos.cercat.service.comment.PostCommentService;
import com.cos.cercat.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class PostCommentCreateUseCase {

    private final PostCommentService postCommentService;
    private final UserService userService;
    private final PostService postService;
    private final AlarmProducer alarmProducer;

    /***
     * 댓글을 생성합니다.
     *
     * @param postId 게시글 ID
     * @param request 게시글 생성 정보
     * @param userId 유저 ID
     */
    @Transactional
    public void createPostComment(Long postId, PostCommentCreateRequest request, Long userId) {
        Post post = postService.getPost(postId);
        User user = userService.getUser(userId);

        PostComment postComment = request.toEntity(post, user);

        if (request.parentCommentId() != null) {
            PostComment parentComment = postCommentService.getPostComment(request.parentCommentId());
            parentComment.addChildComment(postComment);
            alarmProducer.send(createAlarmEvent(post.getUser(), post.getId(), user, AlarmType.NEW_COMMENT_ON_POST));
            alarmProducer.send(createAlarmEvent(parentComment.getUser(), post.getId(), user, AlarmType.NEW_COMMENT_ON_COMMENT));
            log.info("parentCommentId - {}  user - {} 대댓글 생성", request.parentCommentId(), user.getEmail());
            return;
        }

        post.addComment(postComment);
        alarmProducer.send(createAlarmEvent(post.getUser(), postId, user, AlarmType.NEW_COMMENT_ON_POST));
        log.info("user - {} 댓글 생성", user.getEmail());
    }

    private AlarmEvent createAlarmEvent(User toUser, Long postId, User fromUser, AlarmType alarmType) {
        return AlarmEvent.of(
                toUser,
                AlarmArg.of(fromUser, postId),
                alarmType
        );
    }

}
