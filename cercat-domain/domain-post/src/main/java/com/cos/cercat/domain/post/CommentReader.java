package com.cos.cercat.domain.post;

import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReader {

    private final ReadPostRepository postRepository;


    public PostComment read(TargetComment targetComment) {
        return postRepository.find(targetComment);
    }

    public SliceResult<PostComment> read(User user, Cursor cursor) {
        return postRepository.findComment(user, cursor);
    }

    public PostComment readToLike(TargetComment targetComment) {
        return postRepository.findCommentWithLock(targetComment);
    }

}
