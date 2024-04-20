package com.cos.cercat.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AlarmType {
    NEW_LIKE_ON_POST,
    NEW_COMMENT_ON_POST,
    NEW_LIKE_ON_COMMENT,
    NEW_COMMENT_ON_COMMENT,
    BEFORE_APPLICATION,
    START_APPLICATION,
    DEADLINE
}
