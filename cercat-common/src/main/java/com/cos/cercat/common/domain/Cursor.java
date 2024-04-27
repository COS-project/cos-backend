package com.cos.cercat.common.domain;

public record Cursor(
        Integer page,
        Integer size,
        String sortKey,
        SortDirection sortDirection
) {
    private static final String DEFAULT_SORT_KEY = "createdAt";

    public Cursor {
        // 기본값 설정
        if (page == null) {
            page = 0; // 기본 페이지 번호
        }

        if (size == null) {
            size = 10; // 기본 페이지 크기
        }

        if (sortKey == null) {
            sortKey = DEFAULT_SORT_KEY ; // 기본 정렬 키
        }

        if (sortDirection == null) {
            sortDirection = SortDirection.DESC; // 기본 정렬 방향
        }
    }
}
