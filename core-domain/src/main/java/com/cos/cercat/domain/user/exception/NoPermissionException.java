package com.cos.cercat.domain.user.exception;

import com.cos.cercat.exception.DomainException;

public class NoPermissionException extends DomainException {

    public static final DomainException EXCEPTION = new NoPermissionException();

    private NoPermissionException() {
        super(UserErrorCode.NO_PERMISSION_ERROR);
    }
}
