package com.cos.cercat.infra.exception;

import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.InfraException;

public class EntityTypeMismatchException extends InfraException {

        public static final InfraException EXCEPTION = new EntityTypeMismatchException();

        private EntityTypeMismatchException() {
            super(GlobalErrorCode.TYPE_MISMATCH);
        }
}
