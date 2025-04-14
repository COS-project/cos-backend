package com.cos.cercat.alarm.infrastructure.sse.exception;

import com.cos.cercat.exception.BaseErrorCode;
import com.cos.cercat.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SSEErrorCode implements BaseErrorCode {

    CONNECT_ERROR("SSE_001",HttpStatus.INTERNAL_SERVER_ERROR.value(), "SSE 연결 중 오류가 발생하였습니다."),
    ALARM_SEND_ERROR("SSE_002",HttpStatus.INTERNAL_SERVER_ERROR.value(), "알람 전송 중 오류가 발생하였습니다.");

    private final String code;
    private final int status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(code, status, message);
    }
}
