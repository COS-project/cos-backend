package com.cos.cercat.common.domain;

public record Cursor(
        int page,
        long cursor,
        int limit,
        SortKey sortKey,
        SortDirection sortDirection
) {

}
