package com.cos.cercat.domain.certificate.exception;

import com.cos.cercat.common.exception.BaseErrorCode;
import com.cos.cercat.common.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CertificateErrorCode implements BaseErrorCode {

    RECENT_CERTIFICATE_EXAM_NOT_EXIST(400, "최근 자격시험이 존재하지 않습니다."),
    ;


    private final Integer status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(status, message);
    }
}
