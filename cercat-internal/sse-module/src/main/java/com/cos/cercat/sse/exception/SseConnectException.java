package com.cos.cercat.sse.exception;

import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.InfraException;

public class SseConnectException extends InfraException {

        public static final InfraException EXCEPTION = new SseConnectException();

        private SseConnectException() {
            super(GlobalErrorCode.SSE_CONNECT_ERROR);
        }
}
