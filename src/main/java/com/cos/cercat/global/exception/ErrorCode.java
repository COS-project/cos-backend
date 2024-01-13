package com.cos.cercat.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    MOCK_EXAM_NOT_FOUND(HttpStatus.NOT_FOUND, "mock exam not founded"),
    SUBJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "subject not founded"),
    CERTIFICATE_NOT_FOUND(HttpStatus.NOT_FOUND, "certificate not founded"),
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "question not founded"),
    INCORRECT_FILE_FORMAT(HttpStatus.BAD_REQUEST, "incorrect file format"),
    INVALID_PASSWORD(HttpStatus.NOT_FOUND, "invalid password"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "bad request"),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "unauthorized user"),
    IMAGE_UPLOAD_FAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "image upload fail"),
    NO_PERMISSION_ERROR(HttpStatus.FORBIDDEN, "user have no permission"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "there is internal server error");

    private final HttpStatus httpStatus;
    private final String message;

}
