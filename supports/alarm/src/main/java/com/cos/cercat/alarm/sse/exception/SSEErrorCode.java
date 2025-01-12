package com.cos.cercat.alarm.sse.exception;

import com.cos.cercat.common.exception.BaseErrorCode;
import com.cos.cercat.common.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SSEErrorCode implements BaseErrorCode {

    CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SSE 연결 중 오류가 발생하였습니다."),
    ALARM_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "알람 전송 중 오류가 발생하였습니다.");

    private final int status;
    private final String message;


    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(status, message);
    }
}
