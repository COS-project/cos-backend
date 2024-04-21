package com.cos.cercat.common.domain;

import java.util.List;

public record SliceResult<T>(
        List<T> content,
        boolean hasNext
) {

        public static <T> SliceResult<T> of(List<T> content, boolean hasNext) {
            return new SliceResult<>(content, hasNext);
        }
}
