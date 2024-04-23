package com.cos.cercat.domain.post;

import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;

import java.util.List;

public interface ReadPostRepository {

    SliceResult<Post> search(TargetCertificate targetCertificate,
                             CommentaryPostSearchCond commentaryPostSearchCond,
                             Cursor cursor);

    PostWithComments findDetail(TargetPost targetPost);

    Post findWithLock(TargetPost targetPost);

    PostComment findCommentWithLock(TargetComment targetComment);

    PostComment find(TargetComment targetComment);

    SliceResult<PostComment> findComment(TargetUser targetUser, Cursor cursor);

    List<Post> findTop3TipPosts(TargetCertificate targetCertificate);

    SliceResult<Post> findMyCommentaryPosts(TargetUser targetUser, Cursor cursor);

    SliceResult<Post> findMyNormalPosts(TargetUser targetUser, Cursor cursor);

    SliceResult<Post> findMyTipPosts(TargetUser targetUser, Cursor cursor);

    Post find(TargetPost targetPost);
}
