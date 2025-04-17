package com.cos.cercat.database.post.repository;

import static com.cos.cercat.database.like.entity.QLikeCountEntity.likeCountEntity;
import static com.cos.cercat.database.post.entity.QPostCommentEntity.postCommentEntity;

import com.cos.cercat.database.common.util.PagingUtil;
import com.cos.cercat.database.like.entity.QLikeCountEntity;
import com.cos.cercat.database.post.entity.PostCommentEntity;
import com.cos.cercat.database.post.entity.QPostCommentEntity;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.like.LikeTargetType;
import com.cos.cercat.domain.user.User;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.DateTimeTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class PostCommentCustomRepositoryImpl implements PostCommentCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public SliceResult<Long> findDistinctPostIdsByUser(User user, Pageable pageable) {
        QPostCommentEntity postComment = postCommentEntity;
        QLikeCountEntity likeCount = likeCountEntity;

        List<Long> postIds = queryFactory
                .select(postComment.postId)
                .from(postComment)
                .join(likeCount)
                .on(postComment.id.eq(likeCount.id.targetId)
                        .and(likeCount.id.targetType.eq(LikeTargetType.COMMENT)))
                .where(postComment.userEntity.id.eq(user.getId()))
                .groupBy(postComment.postId)
                .orderBy(sort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (postIds.size() > pageable.getPageSize()) {
            postIds.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceResult<>(postIds, hasNext);
    }

    private static OrderSpecifier<?> sort(Pageable pageable) {
        DateTimeTemplate<LocalDateTime> maxCreatedAt =
                Expressions.dateTimeTemplate(LocalDateTime.class, "MAX({0})", postCommentEntity.createdAt);

        NumberTemplate<Long> maxLikeCount =
                Expressions.numberTemplate(Long.class, "MAX({0})", likeCountEntity.count);

        return PagingUtil.getOrderSpecifier(pageable, maxCreatedAt, maxLikeCount);
    }

}
