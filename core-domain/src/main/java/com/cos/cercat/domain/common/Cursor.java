package com.cos.cercat.domain.common;


import java.util.List;

public record Cursor(
        Integer page,
        Integer size,
        List<SortOrder> sortOrders
) {
}
