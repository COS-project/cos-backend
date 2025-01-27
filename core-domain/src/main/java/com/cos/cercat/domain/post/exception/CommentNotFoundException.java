package com.cos.cercat.domain.post.exception;

import com.cos.cercat.exception.DomainException;
import com.cos.cercat.exception.GlobalErrorCode;
import com.cos.cercat.exception.InfraException;

public class CommentNotFoundException extends DomainException {
    public static final DomainException EXCEPTION = new CommentNotFoundException();

    private CommentNotFoundException() {
        super(PostErrorCode.POST_COMMENT_NOT_FOUND);
    }
}
