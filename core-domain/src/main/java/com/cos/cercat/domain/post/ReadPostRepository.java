package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.searchlog.SearchCond;
import com.cos.cercat.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface ReadPostRepository {

    SliceResult<Post> findCommentaries(Certificate certificate,
                             CommentarySearchCond commentarySearchCond,
                             Cursor cursor);

    SliceResult<Post> findPosts(Certificate certificate, SearchCond cond, Cursor cursor);

    Optional<Post> find(PostId postId);

    Optional<Post> findForUpdate(PostId postId);

    List<Post> findTop3TipPosts(Certificate certificate);

    SliceResult<Post> findMyPosts(User user, Cursor cursor, PostType postType);
}
