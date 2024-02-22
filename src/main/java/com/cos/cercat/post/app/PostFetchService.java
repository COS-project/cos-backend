package com.cos.cercat.post.app;

import com.cos.cercat.post.domain.PostType;
import com.cos.cercat.post.dto.request.PostSearchCond;
import com.cos.cercat.post.dto.response.PostResponse;
import com.cos.cercat.post.dto.response.PostWithCommentsResponse;
import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.comment.app.PostCommentService;
import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    /***
     * 게시글을 필터에 따라 검색합니다.
     * @param pageable 페이징 정보
     * @param postType COMMENTARY(해설), TIP(꿀팁), NORMAL(자유)
     * @param certificateId 자격증 ID
     * @param cond 검색 필터
     * @return Slice 형태의 게시글 Response DTO를 반환합니다.
     */
    public Slice<PostResponse> searchPosts(Pageable pageable, PostType postType, Long certificateId, PostSearchCond cond) {
        Certificate certificate = certificateService.getCertificate(certificateId);

        log.info("certificate - {}, postType - {}, cond - {} 게시글 검색",
                certificate.getCertificateName(), postType, cond);
        return switch (postType) {
            case COMMENTARY -> commentaryPostService
                    .searchCommentaryPosts(pageable, certificate, cond)
                    .map(PostResponse::from);
            case TIP -> tipPostService
                    .searchTipPosts(pageable, certificate, cond)
                    .map(PostResponse::from);
            case NORMAL -> normalPostService
                    .searchNormalPosts(pageable, certificate, cond)
                    .map(PostResponse::from);
        };
    }

    /***
     * 게시글 상세정보를 조회합니다.
     * @param postId 게시글 ID
     * @return 댓글을 포함한 게시글 상세정보를 반환합니다.
     */
    public PostWithCommentsResponse getPostDetail(Long postId) {
        log.info("postId - {} 게시글 상세정보 조회", postId);
        return PostWithCommentsResponse.from(postService.getPost(postId));
    }

    /***
     * 해당 자격증의 꿀팁 게시글 인기 TOP3를 조회한다.
     * @param certificateId 자격증 ID
     * @return 리스트 형태의 게시글 정보
     */
    public List<PostResponse> getTop3TipPosts(Long certificateId) {
        Certificate certificate = certificateService.getCertificate(certificateId);

        log.info("certificate - {} 자격증 Best TOP3 게시글 조회", certificate.getCertificateName());
        return tipPostService.getTop3TipPosts(certificate).stream()
                .map(PostResponse::from)
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
            case COMMENTARY -> commentaryPostService.getMyCommentaryPosts(user, pageable).map(PostResponse::from);
            case TIP -> tipPostService.getMyTipPosts(user, pageable).map(PostResponse::from);
            case NORMAL -> normalPostService.getMyNormalPosts(user, pageable).map(PostResponse::from);
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
                .map(PostResponse::from);
    }
}
