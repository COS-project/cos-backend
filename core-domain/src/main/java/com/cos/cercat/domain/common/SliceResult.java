package com.cos.cercat.domain.common;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public record SliceResult<T>(
        List<T> content,
        boolean hasNext
) {

    public static <T> SliceResult<T> of(List<T> content, boolean hasNext) {
        return new SliceResult<>(content, hasNext);
    }

    public <R> SliceResult<R> map(Function<T, R> mapper) {
        List<R> mappedContent = content.stream()
                .map(mapper)
                .collect(Collectors.toList());
        return new SliceResult<>(mappedContent, hasNext);
    }
}
