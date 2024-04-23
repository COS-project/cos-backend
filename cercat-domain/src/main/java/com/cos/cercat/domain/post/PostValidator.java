package com.cos.cercat.domain.post;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidator {

    private final PostReader postReader;


    //대댓글이 작성이 정상적인지 검증
    public void validate(TargetComment targetComment, TargetPost targetPost) {
        PostComment parentComment = postReader.readComment(targetComment);
        if (parentComment.getPostId() != targetPost.postId()) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }

}
