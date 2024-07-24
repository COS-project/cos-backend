package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateReader;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.user.TargetUser;
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
    private final CommentReader commentReader;
    private final PostSearcher postSearcher;

    @Transactional(readOnly = true)
    public SliceResult<Post> searchCommentaryPost(TargetCertificate targetCertificate,
                                                  CommentaryPostSearchCond commentaryPostSearchCond,
                                                  Cursor cursor) {
        Certificate certificate = certificateReader.read(targetCertificate);
        return postSearcher.search(certificate, commentaryPostSearchCond, cursor);
    }

    public PostWithComments readDetail(TargetPost targetPost) {
        return postReader.readDetail(targetPost);
    }

    public List<Post> readTop3TipPosts(TargetCertificate targetCertificate) {
        Certificate certificate = certificateReader.read(targetCertificate);
        return postReader.readTop3TipPosts(certificate);
    }

    public SliceResult<Post> readMyPosts(TargetUser targetUser,
                                         PostType postType,
                                         Cursor cursor) {
        User user = userReader.read(targetUser);
        return postReader.readMyPosts(user, postType, cursor);
    }

    public SliceResult<Post> readCommentingPosts(TargetUser targetUser,
                                                Cursor cursor) {
        User user = userReader.read(targetUser);
        SliceResult<PostComment> postComments = commentReader.read(user, cursor);
        return postComments.map(postComment ->
                postReader.read(TargetPost.from(postComment.getPostId()))
        );
    }

}
