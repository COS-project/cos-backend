package com.cos.cercat.board.repository;

import com.cos.cercat.board.domain.CommentaryPost;
import com.cos.cercat.certificate.domain.Certificate;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cos.cercat.board.domain.QCommentaryPost.*;
import static com.cos.cercat.user.domain.QUser.*;

@Repository
@RequiredArgsConstructor
public class CommentaryPostRepositoryImpl implements CommentaryPostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<CommentaryPost> searchCommentaryPosts(Pageable pageable, Certificate certificate, String keyword) {
        List<CommentaryPost> contents = queryFactory.select(commentaryPost)
                .from(commentaryPost)
                .leftJoin(commentaryPost.user, user)
                .where(
                        containKeyword(keyword),
                        commentaryPost.certificate.eq(certificate)
                )
                .orderBy(commentaryPostSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(commentaryPost.count())
                .from(commentaryPost);

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    private OrderSpecifier<?> commentaryPostSort(Pageable pageable) {
        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "createdAt" -> {
                        return new OrderSpecifier<>(direction, commentaryPost.createdAt);
                    }
                    case "likeCount" -> {
                        return new OrderSpecifier<>(direction, commentaryPost.likeCount);
                    }
                }
            }
        }
        return null;
    }

    private BooleanExpression containKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }

        return commentaryPost.title.containsIgnoreCase(keyword).or(commentaryPost.content.containsIgnoreCase(keyword));
    }
}
