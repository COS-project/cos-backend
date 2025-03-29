package com.cos.cercat.gcs.exception;

import com.cos.cercat.exception.BaseErrorCode;
import com.cos.cercat.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GcsErrorCode implements BaseErrorCode {

    IMAGE_UPLOAD_FAIL_ERROR("GCS_001", 500, "image upload fail"),
    ;

    private final String code;
    private final Integer status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(code, status, message);
    }
}
