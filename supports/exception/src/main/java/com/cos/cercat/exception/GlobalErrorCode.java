package com.cos.cercat.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode {

    MOCK_EXAM_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "mock exam not founded"),
    GOAL_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "goal not founded"),
    MOCK_EXAM_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "mock exam result not founded"),
    EXAM_INFO_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "exam info not founded"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "post not founded"),
    SEARCH_POST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "search post not founded"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "comment not founded"),
    UNKNOWN_POST_TYPE(HttpStatus.BAD_REQUEST.value(), "post type unknown"),
    SUBJECT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "subject not founded"),
    CERTIFICATE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "certificate not founded"),
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "question not founded"),
    INCORRECT_FILE_FORMAT(HttpStatus.BAD_REQUEST.value(), "incorrect file format"),
    REFRESH_TOKEN_REISSUE(HttpStatus.CREATED.value(), "refresh token reissued"),
    GOAL_CREATE_ERROR(HttpStatus.BAD_REQUEST.value(), "can set only one certification per goal"),
    DUPLICATE_EXAM_REVIEW(HttpStatus.BAD_REQUEST.value(), "can write only one exam review per certificate exam"),
    INVALID_PASSWORD(HttpStatus.NOT_FOUND.value(), "invalid password"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "bad request"),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED.value(), "unauthorized user"),
    IMAGE_UPLOAD_FAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "image upload fail"),
    NO_PERMISSION_ERROR(HttpStatus.FORBIDDEN.value(), "user have no permission"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에러입니다. 강지원에게 문의해주세요"),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST.value(), "invalid refresh token"),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), "invalid token"),
    ACCESS_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST.value(), "access token expired"),
    USER_ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "user answer not founded"),
    SEARCH_LOG_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "search log not exist"),
    ES_SEARCH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "엘라스틱 서치 서버에러입니다. 강지원에게 문의해주세요");
    private final Integer status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(status, message);
    }
}
