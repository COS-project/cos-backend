package com.cos.cercat.common.domain;

import com.cos.cercat.common.exception.BaseErrorCode;
import com.cos.cercat.common.exception.GlobalErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@Getter
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Response<T> {

    private String responseCode;

    private String message;

    private T result;

    public Response(String responseCode, T result) {
        this.responseCode = responseCode;
        this.result = result;
    }

    public Response(String responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", result);
    }

    public static Response<Void> success() {
        return new Response<>("SUCCESS", null);
    }

    public static Response<Void> success(String message) {
        return new Response<>("SUCCESS", message);
    }

    public static Response<Void> error(BaseErrorCode errorCode, String message) {
        return new Response<>(errorCode.getErrorReason().status().toString(), message,null);
    }

}
