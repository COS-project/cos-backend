package com.cos.cercat.alarm.infrastructure.sse.exception;

import com.cos.cercat.exception.WebException;

public class SseConnectException extends WebException {

    public static final SseConnectException EXCEPTION = new SseConnectException();

    private SseConnectException() {
        super(SSEErrorCode.CONNECT_ERROR);
    }
}
