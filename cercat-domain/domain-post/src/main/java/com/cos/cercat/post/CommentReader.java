package com.cos.cercat.post;

import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReader {

    private final ReadPostRepository postRepository;


    public PostComment read(TargetComment targetComment) {
        return postRepository.find(targetComment);
    }

    public SliceResult<PostComment> read(TargetUser targetUser, Cursor cursor) {
        return postRepository.findComment(targetUser, cursor);
    }

    public PostComment readToLike(TargetComment targetComment) {
        return postRepository.findCommentWithLock(targetComment);
    }

}
