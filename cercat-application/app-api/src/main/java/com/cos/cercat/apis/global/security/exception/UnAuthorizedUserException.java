package com.cos.cercat.apis.global.security.exception;

import com.cos.cercat.common.exception.WebException;

public class UnAuthorizedUserException extends WebException {

        public static final UnAuthorizedUserException EXCEPTION = new UnAuthorizedUserException();

        private UnAuthorizedUserException() {
            super(SecurityErrorCode.UNAUTHORIZED_USER);
        }
}
