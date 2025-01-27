package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface ReadPostRepository {

    SliceResult<Post> search(Certificate certificate,
                             CommentaryPostSearchCond commentaryPostSearchCond,
                             Cursor cursor);

    Optional<Post> find(TargetPost targetPost);

    List<Post> findTop3TipPosts(Certificate certificate);

    SliceResult<Post> findMyPosts(User user, Cursor cursor, PostType postType);

}
