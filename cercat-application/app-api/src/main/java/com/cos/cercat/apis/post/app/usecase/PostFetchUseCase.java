package com.cos.cercat.apis.post.app.usecase;

import com.cos.cercat.apis.post.dto.response.PostResponse;
import com.cos.cercat.apis.post.dto.response.PostWithCommentsResponse;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.apis.comment.dto.response.PostCommentResponse;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.comment.PostCommentEntity;
import com.cos.cercat.domain.post.PostEntity;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.service.CommentLikeService;
import com.cos.cercat.service.PostLikeService;
import com.cos.cercat.domain.EmbeddedId.CommentLikePK;
import com.cos.cercat.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.domain.like.LikeTargetType;
import com.cos.cercat.domain.post.CommentaryPostSearchCond;
import com.cos.cercat.service.CertificateService;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.service.UserService;
import com.cos.cercat.service.comment.PostCommentService;
import com.cos.cercat.service.post.CommentaryPostService;
import com.cos.cercat.service.post.NormalPostService;
import com.cos.cercat.service.post.PostService;
import com.cos.cercat.service.post.TipPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@UseCase
@Slf4j
@Transactional(readOnly = true)
public class PostFetchUseCase {

    private final CommentaryPostService commentaryPostService;
    private final CertificateService certificateService;
    private final NormalPostService normalPostService;
    private final TipPostService tipPostService;
    private final UserService userService;
    private final PostService postService;
    private final PostCommentService postCommentService;
    private final PostLikeService postLikeService;
    private final CommentLikeService commentLikeService;

    /***
     * 해당 자격증의 꿀팁 게시글 인기 TOP3를 조회한다.
     * @param certificateId 자격증 ID
     * @return 리스트 형태의 게시글 정보
     */
    public List<PostResponse> getTop3TipPosts(Long certificateId, Long userId) {
        CertificateEntity certificateEntity = certificateService.getCertificate(certificateId);

        log.info("certificateEntity - {} 자격증 Best TOP3 게시글 조회", certificateEntity.getCertificateName());
        return tipPostService.getTop3TipPosts(certificateEntity).stream()
                .map(post -> PostResponse.of(post, isLiked(LikeTargetType.POST, userId, post.getId())))
                .toList();
    }

    /***
     * 내가 쓴 게시글을 게시글 타입에 따라 조회합니다.
     * @param postType COMMENTARY(해설), TIP(꿀팁), NORMAL(자유)
     * @param userId 유저 ID
     * @param pageable 페이징 정보
     * @return 내가 쓴 게시글들을 반환합니다.
     */
    public Slice<PostResponse> getMyPosts(PostType postType, Long userId, Pageable pageable) {
        UserEntity userEntity = userService.getUser(userId);

        log.info("userEntity - {} 가 쓴 게시글 조회", userEntity.getEmail());
        return switch (postType) {
            case COMMENTARY -> commentaryPostService.getMyCommentaryPosts(userEntity, pageable).map(post -> PostResponse.of(post, isLiked(LikeTargetType.POST, userId, post.getId())));
            case TIP -> tipPostService.getMyTipPosts(userEntity, pageable).map(post -> PostResponse.of(post, isLiked(LikeTargetType.POST, userId, post.getId())));
            case NORMAL -> normalPostService.getMyNormalPosts(userEntity, pageable).map(post -> PostResponse.of(post, isLiked(LikeTargetType.POST, userId, post.getId())));
        };
    }

    /***
     * 내가 댓글을 쓴 게시글들을 반환합니다.
     * @param userId 유저 ID
     * @param pageable 페이징 정보
     * @return 유저 본인이 댓글을 쓴 게시글들을 반환한다.
     */
    public Slice<PostResponse> getMyCommentPosts(Long userId, Pageable pageable) {
        UserEntity userEntity = userService.getUser(userId);

        log.info("userEntity - {} 가 댓글을 쓴 게시글 조회", userEntity.getEmail());
        return postCommentService.getMyPostComments(userEntity, pageable)
                .map(PostCommentEntity::getPostEntity)
                .map(post -> PostResponse.of(post, isLiked(LikeTargetType.POST, userId, post.getId())));
    }

    private List<PostCommentResponse> organizeChildComments(List<PostCommentResponse> postComments) {
        Map<Long, PostCommentResponse> map = postComments.stream()
                .collect(Collectors.toMap(PostCommentResponse::postCommentId, Function.identity()));

        map.values().stream()
                .filter(PostCommentResponse::hasParentComment)
                .forEach(postComment -> {
                    PostCommentResponse parentComment = map.get(postComment.parentCommentId());
                    parentComment.addChildComment(postComment);
                });

        return map.values().stream()
                .filter(postComment -> !postComment.hasParentComment())
                .sorted(Comparator
                        .comparing(PostCommentResponse::createdAt)
                        .reversed()
                        .thenComparing(PostCommentResponse::postCommentId))
                .collect(Collectors.toList());
    }

    private boolean isLiked(LikeTargetType likeTargetType, Long userId, Long postId) {
        return switch (likeTargetType) {
            case POST -> postLikeService.existsLike(PostLikePK.of(userId, postId));
            case COMMENT -> commentLikeService.existsLike(CommentLikePK.of(userId, postId));
        };
    }
}
