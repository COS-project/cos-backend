package com.cos.cercat.sse.exception;

import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.WebException;

public class SseConnectException extends WebException {

        public static final SseConnectException EXCEPTION = new SseConnectException();

        private SseConnectException() {
            super(GlobalErrorCode.SSE_CONNECT_ERROR);
        }
}
