package com.cos.cercat.post.repository;

import com.cos.cercat.post.domain.NormalPost;
import com.cos.cercat.global.util.PagingUtil;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.QCertificate;
import com.cos.cercat.post.dto.request.PostSearchCond;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cos.cercat.post.domain.QNormalPost.normalPost;
import static com.cos.cercat.user.domain.QUser.user;


@Repository
@RequiredArgsConstructor
public class NormalPostRepositoryImpl implements PostRepositoryCustom<NormalPost> {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<NormalPost> searchPosts(Pageable pageable, Certificate certificate, PostSearchCond cond) {
        List<NormalPost> contents = queryFactory
                .selectFrom(normalPost)
                .leftJoin(normalPost.user, user)
                .fetchJoin()
                .leftJoin(normalPost.certificate, QCertificate.certificate)
                .fetchJoin()
                .where(
                        containKeyword(cond.keyword()),
                        normalPost.certificate.eq(certificate)
                )
                .orderBy(postSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(normalPost.count())
                .from(normalPost);

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    private BooleanExpression containKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }

        return normalPost.title.containsIgnoreCase(keyword).or(normalPost.content.containsIgnoreCase(keyword));
    }


    private OrderSpecifier<?> postSort(Pageable pageable) {
        return PagingUtil.getOrderSpecifier(pageable, normalPost.createdAt, normalPost.likeCount);
    }

}
