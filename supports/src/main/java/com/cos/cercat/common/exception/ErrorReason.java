package com.cos.cercat.common.exception;

public record ErrorReason(
        Integer status,
        String message
) {
    public static ErrorReason from(Integer status, String message) {
        return new ErrorReason(status, message);
    }
}
