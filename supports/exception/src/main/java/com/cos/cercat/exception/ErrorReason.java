package com.cos.cercat.exception;

public record ErrorReason(
        Integer status,
        String message
) {
    public static ErrorReason from(Integer status, String message) {
        return new ErrorReason(status, message);
    }
}
