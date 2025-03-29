package com.cos.cercat.exception;

public record ErrorReason(
        String code,
        Integer status,
        String message
) {
    public static ErrorReason from(String code, Integer status, String message) {
        return new ErrorReason(code, status, message);
    }
}
