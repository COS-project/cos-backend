package com.cos.cercat.domain.exception;

import com.cos.cercat.common.exception.DomainException;

public class InvalidCommentException extends DomainException {

    public static final InvalidCommentException EXCEPTION = new InvalidCommentException();

    private InvalidCommentException() {
        super(PostErrorCode.INVALID_COMMENT_REQUEST);
    }
}
