package com.cos.cercat.sse.exception;

import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.InfraException;

public class AlarmSendException extends InfraException {
    public static final InfraException EXCEPTION = new AlarmSendException();

    private AlarmSendException() {
        super(GlobalErrorCode.ALARM_SEND_ERROR);
    }
}
