package com.cos.cercat.database.common.util;

import com.cos.cercat.domain.common.Cursor;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

public class PagingUtil {

    public static OrderSpecifier<?> getOrderSpecifier(Pageable pageable,
            Expression<? extends Comparable> createdAt,
            Expression<? extends Comparable> likeCount) {
        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                return switch (order.getProperty()) {
                    case "createdAt" -> new OrderSpecifier<>(direction, createdAt);
                    case "count" -> new OrderSpecifier<>(direction, likeCount);
                    default -> null;
                };
            }
        }
        return null;
    }

    public static PageRequest toPageRequest(Cursor cursor) {
        Sort sort = Sort.by(cursor.sortOrders().stream()
                .map(order -> new Sort.Order(
                        Sort.Direction.fromOptionalString(order.direction().name()).orElse(Sort.Direction.ASC),
                        order.key()
                ))
                .toList());

        return PageRequest.of(cursor.page(), cursor.size(), sort);
    }
}
