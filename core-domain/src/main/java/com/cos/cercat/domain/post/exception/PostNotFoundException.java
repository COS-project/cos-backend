package com.cos.cercat.domain.post.exception;

import com.cos.cercat.exception.DomainException;
import com.cos.cercat.exception.GlobalErrorCode;
import com.cos.cercat.exception.InfraException;

public class PostNotFoundException extends DomainException {
    public static final DomainException EXCEPTION = new PostNotFoundException();

    private PostNotFoundException() {
        super(PostErrorCode.POST_NOT_FOUND);
    }
}
