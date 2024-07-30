package com.cos.cercat.exception;

import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.InfraException;

public class PostNotFoundException extends InfraException {
    public static final InfraException EXCEPTION = new PostNotFoundException();

    private PostNotFoundException() {
        super(GlobalErrorCode.POST_NOT_FOUND);
    }
}
