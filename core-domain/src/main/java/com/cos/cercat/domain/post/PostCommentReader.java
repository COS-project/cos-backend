package com.cos.cercat.domain.post;

import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.post.exception.CommentNotFoundException;
import com.cos.cercat.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCommentReader {

    private final PostCommentRepository postCommentRepository;

    public PostComment read(CommentId commentId) {
        return postCommentRepository.find(commentId).orElseThrow(() -> CommentNotFoundException.EXCEPTION);
    }

    public List<PostComment> readByPost(PostId postId) {
        return postCommentRepository.findCommentsByPost(postId);
    }

    public SliceResult<PostComment> read(User user, Cursor cursor) {
        return postCommentRepository.findCommentsByUser(user, cursor);
    }

}
