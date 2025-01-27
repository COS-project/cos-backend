package com.cos.cercat.domain.post;

import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.user.User;
import java.util.List;
import java.util.Optional;

public interface PostCommentRepository {

    Long save(PostComment postComment);

    List<PostComment> findCommentsByPost(TargetPost targetPost);

    Optional<PostComment> find(TargetComment targetComment);

    SliceResult<PostComment> findCommentsByUser(User user, Cursor cursor);

    void deleteComment(PostComment postComment);

}
