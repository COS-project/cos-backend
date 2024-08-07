package com.cos.cercat.apis.global.api;

import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.GlobalErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomControllerAdvice {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<?> errorHandler(CustomException e) {
        return ResponseEntity.status(HttpStatus.valueOf(e.getErrorCode().getErrorReason().status()))
                .body(Response.error(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(value = InsufficientAuthenticationException.class)
    public ResponseEntity<?> errorHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Response.error(GlobalErrorCode.UNAUTHORIZED_USER, e.getMessage()));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> unknownErrorHandler(Exception e) {
        log.error("CustomControllerAdvice", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error(GlobalErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}
