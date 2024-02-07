package com.cos.cercat.post.repository;

import com.cos.cercat.post.domain.QRecommendTag;
import com.cos.cercat.post.domain.TipPost;
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
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cos.cercat.post.domain.QRecommendTag.*;
import static com.cos.cercat.post.domain.QTipPost.tipPost;
import static com.cos.cercat.user.domain.QUser.user;


@Repository
@RequiredArgsConstructor
public class TipPostRepositoryImpl implements PostRepositoryCustom<TipPost>{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<TipPost> searchPosts(Pageable pageable, Certificate certificate, PostSearchCond cond) {
        List<TipPost> contents = queryFactory
                .selectFrom(tipPost)
                .leftJoin(tipPost.user, user)
                .fetchJoin()
                .leftJoin(tipPost.certificate, QCertificate.certificate)
                .fetchJoin()
                .leftJoin(tipPost.recommendTags.recommendTags, recommendTag)
                .where(
                        containKeyword(cond.keyword()),
                        tipPost.certificate.eq(certificate)
                )
                .orderBy(postSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (contents.size() > pageable.getPageSize()) {
            contents.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(contents, pageable, hasNext);
    }

    private BooleanExpression containKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }

        return tipPost.title.containsIgnoreCase(keyword)
                .or(tipPost.content.containsIgnoreCase(keyword))
                .or(recommendTag.tagName.containsIgnoreCase(keyword));
    }


    private OrderSpecifier<?> postSort(Pageable pageable) {
        return PagingUtil.getOrderSpecifier(pageable, tipPost.createdAt, tipPost.likeCount);
    }
}
