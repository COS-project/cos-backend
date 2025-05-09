package com.cos.cercat.domain.post;

import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.user.User;
import java.util.List;
import java.util.Optional;

public interface PostCommentRepository {

    PostComment save(PostComment postComment);

    List<PostComment> findCommentsByPost(PostId postId);

    Optional<PostComment> find(CommentId commentId);

    SliceResult<Long> findpostIdsByUser(User user, Cursor cursor);

    void deleteComment(PostComment postComment);

}
