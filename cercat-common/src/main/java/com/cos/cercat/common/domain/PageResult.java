package com.cos.cercat.common.domain;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public record PageResult<T>(
        List<T> content,
        int currentPage,
        int pageSize
) {

    public static <T> PageResult<T> of(List<T> content, int currentPage, int pageSize) {
        return new PageResult<>(content, currentPage, pageSize);
    }

    public <R> PageResult<R> map(Function<T, R> mapper) {
        List<R> mappedContent = content.stream()
                .map(mapper)
                .collect(Collectors.toList());
        return new PageResult<>(
                mappedContent,
                currentPage,
                pageSize
        );
    }
}
