package com.cos.cercat.post;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;

import java.util.List;

public interface ReadPostRepository {

    SliceResult<Post> search(Certificate certificate,
                             CommentaryPostSearchCond commentaryPostSearchCond,
                             Cursor cursor);

    PostWithComments findDetail(TargetPost targetPost);

    Post findWithLock(TargetPost targetPost);

    PostComment findCommentWithLock(TargetComment targetComment);

    PostComment find(TargetComment targetComment);

    SliceResult<PostComment> findComment(User user, Cursor cursor);

    List<Post> findTop3TipPosts(Certificate certificate);

    SliceResult<Post> findMyCommentaryPosts(User user, Cursor cursor);

    SliceResult<Post> findMyNormalPosts(User user, Cursor cursor);

    SliceResult<Post> findMyTipPosts(User user, Cursor cursor);

    Post find(TargetPost targetPost);
}
