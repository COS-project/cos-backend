package com.cos.cercat.database.post.exception;

import com.cos.cercat.exception.GlobalErrorCode;
import com.cos.cercat.exception.InfraException;

public class CommentNotFoundException extends InfraException {
    public static final InfraException EXCEPTION = new CommentNotFoundException();

    private CommentNotFoundException() {
        super(GlobalErrorCode.COMMENT_NOT_FOUND);
    }
}
