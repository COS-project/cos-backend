package com.cos.cercat.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

public class PagingUtil {

    public static OrderSpecifier<?> getOrderSpecifier(Pageable pageable, DateTimePath<LocalDateTime> createdAt, NumberPath<Integer> likeCount) {
        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "createdAt" -> {
                        return new OrderSpecifier<>(direction, createdAt);
                    }
                    case "likeCount" -> {
                        return new OrderSpecifier<>(direction, likeCount);
                    }
                }
            }
        }
        return null;
    }
}
