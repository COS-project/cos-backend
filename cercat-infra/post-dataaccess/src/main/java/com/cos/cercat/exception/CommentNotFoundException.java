package com.cos.cercat.exception;

import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.InfraException;

public class CommentNotFoundException extends InfraException {
    public static final InfraException EXCEPTION = new CommentNotFoundException();

    private CommentNotFoundException() {
        super(GlobalErrorCode.COMMENT_NOT_FOUND);
    }
}
