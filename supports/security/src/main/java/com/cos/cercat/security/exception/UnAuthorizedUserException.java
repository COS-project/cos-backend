package com.cos.cercat.security.exception;

import com.cos.cercat.exception.WebException;

public class UnAuthorizedUserException extends WebException {

        public static final UnAuthorizedUserException EXCEPTION = new UnAuthorizedUserException();

        private UnAuthorizedUserException() {
            super(SecurityErrorCode.UNAUTHORIZED_USER);
        }
}
