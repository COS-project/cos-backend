package com.cos.cercat.common.domain;

import java.util.List;

public record PageResult<T>(
        List<T> content,
        int currentPage,
        int pageSize
) {
}
