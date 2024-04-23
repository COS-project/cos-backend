package com.cos.cercat.apis.global.util;

import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SortDirection;
import com.cos.cercat.common.domain.SortKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CursorConvertor {

    public static Cursor toCursor(Pageable pageable) {
        return new Cursor(
                pageable.getPageNumber(),
                pageable.getOffset(),
                pageable.getPageSize(),
                new SortKey(pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse("id")),
                SortDirection.valueOf(pageable.getSort().stream().findFirst().map(Sort.Order::getDirection).orElse(Sort.Direction.ASC).name())
        );
    }

}
