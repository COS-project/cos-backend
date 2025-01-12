package com.cos.cercat.common.domain;


import java.util.List;

public record Cursor(
        Integer page,
        Integer size,
        List<SortOrder> sortOrders
) {
}
