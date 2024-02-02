package com.cos.cercat.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    MOCK_EXAM_NOT_FOUND(HttpStatus.NOT_FOUND, "mock exam not founded"),
    GOAL_NOT_FOUND(HttpStatus.NOT_FOUND, "goal not founded"),
    MOCK_EXAM_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND, "mock exam result not founded"),
    EXAM_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "exam info not founded"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "post not founded"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user not founded"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "comment not founded"),
    UNKNOWN_POST_TYPE(HttpStatus.BAD_REQUEST, "post type unknown"),
    SUBJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "subject not founded"),
    CERTIFICATE_NOT_FOUND(HttpStatus.NOT_FOUND, "certificate not founded"),
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "question not founded"),
    INCORRECT_FILE_FORMAT(HttpStatus.BAD_REQUEST, "incorrect file format"),
    REFRESH_TOKEN_REISSUE(HttpStatus.CREATED, "refresh token reissued"),
    GOAL_CREATE_ERROR(HttpStatus.BAD_REQUEST, "can set only one certification per goal"),
    DUPLICATE_EXAM_REVIEW(HttpStatus.BAD_REQUEST, "can write only one exam review per certificate exam"),
    INVALID_PASSWORD(HttpStatus.NOT_FOUND, "invalid password"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "bad request"),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "unauthorized user"),
    IMAGE_UPLOAD_FAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "image upload fail"),
    NO_PERMISSION_ERROR(HttpStatus.FORBIDDEN, "user have no permission"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "there is internal server error"),
    ALARM_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "alarm connect error"),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "invalid refresh token"),
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "invalid access token"),
    ACCESS_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "access token expired");

    private final HttpStatus httpStatus;
    private final String message;

}
