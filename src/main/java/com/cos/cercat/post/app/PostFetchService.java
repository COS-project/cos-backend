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
public class PostFetchService {

    private final CommentaryPostService commentaryPostService;
    private final CertificateService certificateService;
    private final NormalPostService normalPostService;
    private final TipPostService tipPostService;
    private final UserService userService;
    private final PostService postService;
    private final PostCommentService postCommentService;


    @Transactional(readOnly = true)
    public Slice<PostResponse> searchPosts(Pageable pageable, PostType postType, Long certificateId, PostSearchCond cond) {
        Certificate certificate = certificateService.getCertificate(certificateId);

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

    @Transactional(readOnly = true)
    public PostWithCommentsResponse getPostDetail(Long postId) {
        return PostWithCommentsResponse.from(postService.getPost(postId));
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getTop3TipPosts(Long certificateId) {
        Certificate certificate = certificateService.getCertificate(certificateId);

        return tipPostService.getTop3TipPosts(certificate).stream()
                .map(PostResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public Slice<PostResponse> getMyPosts(PostType postType, Long userId, Pageable pageable) {

        User user = userService.getUser(userId);

        return switch (postType) {
            case COMMENTARY -> commentaryPostService.getMyCommentaryPosts(user, pageable).map(PostResponse::from);
            case TIP -> tipPostService.getMyTipPosts(user, pageable).map(PostResponse::from);
            case NORMAL -> normalPostService.getMyNormalPosts(user, pageable).map(PostResponse::from);
        };
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getMyCommentPosts(Long userId, Pageable pageable) {
        User user = userService.getUser(userId);
        return postCommentService.getMyPostComments(user, pageable)
                .map(PostComment::getPost)
                .map(PostResponse::from);
    }
}
