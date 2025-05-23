package com.cos.cercat.domain.post.exception;

import com.cos.cercat.exception.BaseErrorCode;
import com.cos.cercat.exception.ErrorReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements BaseErrorCode {

    POST_NOT_FOUND("PST_001", 404, "게시글을 찾을 수 없습니다."),
    POST_COMMENT_NOT_FOUND("PST_002", 404, "댓글을 찾을 수 없습니다."),
    INVALID_COMMENT_REQUEST("PST_003", 400, "댓글 생성 요청이 잘못되었습니다.");

    private final String code;
    private final Integer status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.from(code, status, message);
    }
}
