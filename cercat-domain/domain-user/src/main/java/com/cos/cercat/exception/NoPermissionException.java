package com.cos.cercat.exception;

import com.cos.cercat.common.exception.DomainException;

public class NoPermissionException extends DomainException {

    public static final DomainException EXCEPTION = new NoPermissionException();

    private NoPermissionException() {
        super(UserErrorCode.NO_PERMISSION_ERROR);
    }
}
