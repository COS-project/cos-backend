package com.cos.cercat.sse.exception;

import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.WebException;

public class AlarmSendException extends WebException {
    public static final AlarmSendException EXCEPTION = new AlarmSendException();

    private AlarmSendException() {
        super(GlobalErrorCode.ALARM_SEND_ERROR);
    }
}
