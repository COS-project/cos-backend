package com.cos.cercat.apis.like.app.usecase;

import com.cos.cercat.apis.like.app.strategy.LikeStrategy;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.apis.like.app.strategy.LikeStrategyFactory;
import com.cos.cercat.apis.like.app.strategy.StrategyName;
import com.cos.cercat.apis.like.dto.request.LikeType;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.comment.PostComment;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.service.UserService;
import com.cos.cercat.service.comment.PostCommentService;
import com.cos.cercat.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Slf4j
@RequiredArgsConstructor
public class LikeFlipUseCase {

    private final PostService postService;
    private final PostCommentService postCommentService;
    private final UserService userService;
    private final LikeStrategyFactory likeStrategyFactory;

    /***
     * 게시글 또는 댓글의 좋아요를 생성하거나 취소합니다.
     * @param likeType 게시글 또는 댓글
     * @param id 게시글 또는 댓글 ID
     * @param userId 유저 ID
     */
    @Transactional
    public void flipLike(LikeType likeType, Long id, Long userId) {

        UserEntity userEntity = userService.getUser(userId);

        switch (likeType) {
            case POST -> {
                Post post = postService.getPostWithLock(id);
                LikeStrategy<Post> strategy = likeStrategyFactory.findStrategy(StrategyName.POST_LIKE_STRATEGY, Post.class);
                strategy.flipLike(post, userEntity);
            }
            case COMMENT -> {
                PostComment postComment = postCommentService.getPostCommentWithLock(id);
                LikeStrategy<PostComment> strategy = likeStrategyFactory.findStrategy(StrategyName.COMMENT_LIKE_STRATEGY, PostComment.class);
                strategy.flipLike(postComment, userEntity);
            }
        }
    }
}
