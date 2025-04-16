package com.cos.cercat.domain.like.exception;


import com.cos.cercat.exception.BaseErrorCode;
import com.cos.cercat.exception.DomainException;

public class LikeException extends DomainException {

    public LikeException(BaseErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public LikeException(BaseErrorCode errorCode) {
        super(errorCode);
    }

    public static LikeException alreadyLiked() {
        return new LikeException(LikeErrorCode.ALREADY_LIKED);
    }

    public static LikeException alreadyNotLiked() {
        return new LikeException(LikeErrorCode.ALREADY_NOT_LIKED);
    }
}
