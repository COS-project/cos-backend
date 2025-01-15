package com.cos.cercat.database.post.exception;

import com.cos.cercat.exception.GlobalErrorCode;
import com.cos.cercat.exception.InfraException;

public class PostNotFoundException extends InfraException {
    public static final InfraException EXCEPTION = new PostNotFoundException();

    private PostNotFoundException() {
        super(GlobalErrorCode.POST_NOT_FOUND);
    }
}
