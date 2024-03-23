package com.cos.cercat.post.app.facade;

import com.cos.cercat.comment.dto.response.PostCommentResponse;
import com.cos.cercat.like.app.CommentLikeService;
import com.cos.cercat.like.app.PostLikeService;
import com.cos.cercat.like.domain.EmbeddedId.CommentLikePK;
import com.cos.cercat.like.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.like.dto.request.LikeType;
import com.cos.cercat.post.app.CommentaryPostService;
import com.cos.cercat.post.app.NormalPostService;
import com.cos.cercat.post.app.PostService;
import com.cos.cercat.post.app.TipPostService;
import com.cos.cercat.search.domain.PostDocument;
import com.cos.cercat.search.dto.SearchCond;
import com.cos.cercat.search.service.PostSearchService;
import com.cos.cercat.post.domain.Post;
import com.cos.cercat.post.domain.PostType;
import com.cos.cercat.post.dto.request.CommentaryPostSearchCond;
import com.cos.cercat.post.dto.response.PostResponse;
import com.cos.cercat.post.dto.response.PostWithCommentsResponse;
import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.comment.app.PostCommentService;
import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class PostFetchService {

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
     * 게시글을 필터에 따라 검색합니다.
     * @param pageable 페이징 정보
     * @param certificateId 자격증 ID
     * @param cond 검색 필터
     * @return Slice 형태의 게시글 Response DTO를 반환합니다.
     */
    public Slice<PostResponse> searchCommentaryPosts(Pageable pageable,
                                                     Long certificateId,
                                                     CommentaryPostSearchCond cond,
                                                     Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);

        log.info("certificate - {}, cond - {} 해설게시글 검색", certificate.getCertificateName(), cond);
        return commentaryPostService.searchCommentaryPosts(pageable, certificate, cond)
                .map(post -> PostResponse.of(post, isLiked(LikeType.POST, userId, post.getId())));
    }

    /***
     * 게시글 상세정보를 조회합니다.
     * @param postId 게시글 ID
     * @return 댓글을 포함한 게시글 상세정보를 반환합니다.
     */
    public PostWithCommentsResponse getPostDetail(Long postId, Long userId) {
        log.info("postId - {} 게시글 상세정보 조회", postId);

        Post post = postService.getPost(postId);

        List<PostCommentResponse> postCommentResponses = organizeChildComments( // 대댓글을 댓글의 children으로 정리
                post.getPostComments().getAll()
                        .stream()
                        .map(postComment -> PostCommentResponse.of(postComment, isLiked(LikeType.COMMENT, userId, postComment.getId())))
                        .toList()
        );

        return PostWithCommentsResponse.of(postService.getPost(postId), postCommentResponses, isLiked(LikeType.POST, userId, postId));
    }

    /***
     * 해당 자격증의 꿀팁 게시글 인기 TOP3를 조회한다.
     * @param certificateId 자격증 ID
     * @return 리스트 형태의 게시글 정보
     */
    public List<PostResponse> getTop3TipPosts(Long certificateId, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);

        log.info("certificate - {} 자격증 Best TOP3 게시글 조회", certificate.getCertificateName());
        return tipPostService.getTop3TipPosts(certificate).stream()
                .map(post -> PostResponse.of(post, isLiked(LikeType.POST, userId, post.getId())))
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
        User user = userService.getUser(userId);

        log.info("user - {} 가 쓴 게시글 조회", user.getEmail());
        return switch (postType) {
            case COMMENTARY -> commentaryPostService.getMyCommentaryPosts(user, pageable).map(post -> PostResponse.of(post, isLiked(LikeType.POST, userId, post.getId())));
            case TIP -> tipPostService.getMyTipPosts(user, pageable).map(post -> PostResponse.of(post, isLiked(LikeType.POST, userId, post.getId())));
            case NORMAL -> normalPostService.getMyNormalPosts(user, pageable).map(post -> PostResponse.of(post, isLiked(LikeType.POST, userId, post.getId())));
        };
    }

    /***
     * 내가 댓글을 쓴 게시글들을 반환합니다.
     * @param userId 유저 ID
     * @param pageable 페이징 정보
     * @return 유저 본인이 댓글을 쓴 게시글들을 반환한다.
     */
    public Slice<PostResponse> getMyCommentPosts(Long userId, Pageable pageable) {
        User user = userService.getUser(userId);

        log.info("user - {} 가 댓글을 쓴 게시글 조회", user.getEmail());
        return postCommentService.getMyPostComments(user, pageable)
                .map(PostComment::getPost)
                .map(post -> PostResponse.of(post, isLiked(LikeType.POST, userId, post.getId())));
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

    private boolean isLiked(LikeType likeType, Long userId, Long postId) {
        return switch (likeType) {
            case POST -> postLikeService.existsLike(PostLikePK.of(userId, postId));
            case COMMENT -> commentLikeService.existsLike(CommentLikePK.of(userId, postId));
        };
    }
}
