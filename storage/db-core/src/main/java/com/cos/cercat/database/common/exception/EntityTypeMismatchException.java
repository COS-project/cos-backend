package com.cos.cercat.database.common.exception;

import com.cos.cercat.common.exception.InfraException;

public class EntityTypeMismatchException extends InfraException {

        public static final InfraException EXCEPTION = new EntityTypeMismatchException();

        private EntityTypeMismatchException() {
            super(DBErrorCode.ENTITY_TYPE_MISMATCH);
        }
}
