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

    public PostComment read(TargetComment targetComment) {
        return postCommentRepository.find(targetComment).orElseThrow(() -> CommentNotFoundException.EXCEPTION);
    }

    public List<PostComment> readByPost(TargetPost targetPost) {
        return postCommentRepository.findCommentsByPost(targetPost);
    }

    public SliceResult<PostComment> read(User user, Cursor cursor) {
        return postCommentRepository.findCommentsByUser(user, cursor);
    }

}
