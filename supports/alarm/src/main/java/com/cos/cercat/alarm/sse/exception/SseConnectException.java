package com.cos.cercat.alarm.sse.exception;

import com.cos.cercat.common.exception.WebException;

public class SseConnectException extends WebException {

    public static final SseConnectException EXCEPTION = new SseConnectException();

    private SseConnectException() {
        super(SSEErrorCode.CONNECT_ERROR);
    }
}
