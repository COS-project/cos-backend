package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateReader;
import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.searchlog.SearchCond;
import com.cos.cercat.domain.searchlog.SearchLogAppender;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadPostService {

    private final CertificateReader certificateReader;
    private final UserReader userReader;
    private final PostReader postReader;
    private final PostCommentReader postCommentReader;
    private final SearchLogAppender searchLogAppender;

    @Transactional(readOnly = true)
    public SliceResult<Post> searchPosts(
            UserId userId,
            CertificateId certificateId,
            SearchCond cond,
            Cursor cursor
    ) {
        User user = userReader.read(userId);
        Certificate certificate = certificateReader.read(certificateId);
        searchLogAppender.append(user, cond.toSearchLog(certificate));
        return postReader.read(certificate, cond, cursor);
    }

    @Transactional(readOnly = true)
    public SliceResult<Post> searchCommentaryPost(
            CertificateId certificateId,
            CommentarySearchCond commentarySearchCond,
            Cursor cursor
    ) {

        Certificate certificate = certificateReader.read(certificateId);
        return postReader.readCommentaries(certificate, commentarySearchCond, cursor);
    }

    public PostWithComments readDetail(PostId postId) {
        return postReader.readDetail(postId);
    }

    public List<Post> readTop3TipPosts(CertificateId certificateId) {
        Certificate certificate = certificateReader.read(certificateId);
        return postReader.readTop3TipPosts(certificate);
    }

    public SliceResult<Post> readMyPosts(UserId userId,
                                         PostType postType,
                                         Cursor cursor) {
        User user = userReader.read(userId);
        return postReader.readMyPosts(user, postType, cursor);
    }

    public SliceResult<Post> readCommentingPosts(UserId userId,
                                                Cursor cursor) {
        User user = userReader.read(userId);
        SliceResult<PostComment> postComments = postCommentReader.read(user, cursor);
        return postComments.map(postComment ->
                postReader.read(PostId.from(postComment.getPostId()))
        );
    }

}
