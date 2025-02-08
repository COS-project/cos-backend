package com.cos.cercat.alarm.sse.exception;

import com.cos.cercat.exception.WebException;

public class AlarmSendException extends WebException {

    public static final AlarmSendException EXCEPTION = new AlarmSendException();

    private AlarmSendException() {
        super(SSEErrorCode.ALARM_SEND_ERROR);
    }
}
