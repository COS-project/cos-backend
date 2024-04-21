package com.cos.cercat.apis.comment.app.usecase;

import com.cos.cercat.apis.alarm.app.kafka.producer.AlarmProducer;
import com.cos.cercat.apis.comment.dto.request.PostCommentCreateRequest;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.domain.AlarmType;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.comment.PostCommentEntity;
import com.cos.cercat.domain.post.PostEntity;
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
        PostEntity postEntity = postService.getPost(postId);
        UserEntity userEntity = userService.getUser(userId);

        PostCommentEntity postCommentEntity = request.toEntity(postEntity, userEntity);

        if (request.parentCommentId() != null) {
            PostCommentEntity parentComment = postCommentService.getPostComment(request.parentCommentId());
            parentComment.addChildComment(postCommentEntity);
            alarmProducer.send(createAlarmEvent(postEntity.getUserEntity(), postEntity.getId(), userEntity, AlarmType.NEW_COMMENT_ON_POST));
            alarmProducer.send(createAlarmEvent(parentComment.getUserEntity(), postEntity.getId(), userEntity, AlarmType.NEW_COMMENT_ON_COMMENT));
            log.info("parentCommentId - {}  userEntity - {} 대댓글 생성", request.parentCommentId(), userEntity.getEmail());
            return;
        }

        postEntity.addComment(postCommentEntity);
        alarmProducer.send(createAlarmEvent(postEntity.getUserEntity(), postId, userEntity, AlarmType.NEW_COMMENT_ON_POST));
        log.info("userEntity - {} 댓글 생성", userEntity.getEmail());
    }

    private AlarmEvent createAlarmEvent(UserEntity toUserEntity, Long postId, UserEntity fromUserEntity, AlarmType alarmType) {
        return AlarmEvent.of(
                toUserEntity,
                AlarmArg.of(fromUserEntity, postId),
                alarmType
        );
    }

}
