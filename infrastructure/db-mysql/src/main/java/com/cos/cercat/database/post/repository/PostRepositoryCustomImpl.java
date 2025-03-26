package com.cos.cercat.database.post.repository;

import static com.cos.cercat.database.post.entity.QPostEntity.postEntity;

import com.cos.cercat.database.common.util.PagingUtil;
import com.cos.cercat.database.like.entity.QLikeCountEntity;
import com.cos.cercat.database.post.entity.PostEntity;
import com.cos.cercat.database.post.entity.QPostEntity;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.like.LikeTargetType;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.searchlog.SearchCond;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<PostEntity> findPosts(SearchCond cond, Certificate certificate, Pageable pageable) {

        List<PostEntity> content = queryFactory
                .selectFrom(postEntity)
                .leftJoin(QLikeCountEntity.likeCountEntity)
                .on(eqPostId())
                .where(
                        eqCertificateId(certificate.id().value()),
                        eqPostType(cond.postType()),
                        fullTextSearch(cond.keyword())

                )
                .orderBy(postSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression eqCertificateId(Long certificateId) {
        return certificateId != null ? postEntity.certificateEntity.id.eq(certificateId) : null;
    }

    private BooleanExpression eqPostType(PostType postType) {
        return postType != null ? postEntity.postType.eq(postType) : null;
    }

    private BooleanExpression fullTextSearch(String keyword) {
        if(keyword == null || keyword.isBlank()) {
            return null;
        }

        NumberTemplate<Double> matchScore = Expressions.numberTemplate(
                Double.class,
                "function('match', {0}, {1}, {2})",
                postEntity.title,
                postEntity.content,
                keyword
        );

        return matchScore.gt(0.0);
    }

    private BooleanExpression eqPostId() {
        return QPostEntity.postEntity.id.eq(QLikeCountEntity.likeCountEntity.id.targetId)
                .and(QLikeCountEntity.likeCountEntity.id.targetType.eq(LikeTargetType.POST));
    }

    public static OrderSpecifier<?> postSort(Pageable pageable) {
        return PagingUtil.getOrderSpecifier(pageable,
                postEntity.createdAt,
                QLikeCountEntity.likeCountEntity.count);
    }
}
