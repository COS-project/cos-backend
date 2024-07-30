package com.cos.cercat.repository;

import com.cos.cercat.domain.*;
import com.cos.cercat.domain.CommentaryPostEntity;
import com.cos.cercat.domain.post.CommentaryPostSearchCond;
import com.cos.cercat.util.PagingUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
@RequiredArgsConstructor
public class CommentaryPostJpaRepositoryImpl implements PostJpaRepositoryCustom<CommentaryPostEntity> {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<CommentaryPostEntity> searchPosts(Pageable pageable, CertificateEntity certificateEntity, CommentaryPostSearchCond cond) {

        List<CommentaryPostEntity> contents = queryFactory
                .selectFrom(QCommentaryPostEntity.commentaryPostEntity)
                .leftJoin(QCommentaryPostEntity.commentaryPostEntity.userEntity, QUserEntity.userEntity)
                .fetchJoin()
                .leftJoin(QCommentaryPostEntity.commentaryPostEntity.questionEntity, QQuestionEntity.questionEntity)
                .fetchJoin()
                .leftJoin(QCommentaryPostEntity.commentaryPostEntity.questionEntity.mockExamEntity, QMockExamEntity.mockExamEntity)
                .fetchJoin()
                .leftJoin(QCommentaryPostEntity.commentaryPostEntity.certificateEntity, QCertificateEntity.certificateEntity)
                .fetchJoin()
                .where(
                        eqExamYear(cond.examYear()),
                        eqRound(cond.round()),
                        eqQuestionSequence(cond.questionSequence()),
                        QCommentaryPostEntity.commentaryPostEntity.certificateEntity.eq(certificateEntity)
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


    private BooleanExpression eqExamYear(Integer examYear) {
        if (examYear == null) return null;


        return QCommentaryPostEntity.commentaryPostEntity.questionEntity.mockExamEntity.examYear.eq(examYear);
    }

    private BooleanExpression eqRound(Integer round) {
        if (round == null) return null;

        return QCommentaryPostEntity.commentaryPostEntity.questionEntity.mockExamEntity.round.eq(round);
    }

    private BooleanExpression eqQuestionSequence(Integer questionSequence) {
        if (questionSequence == null) return null;

        return QCommentaryPostEntity.commentaryPostEntity.questionEntity.questionSeq.eq(questionSequence);
    }

    public static OrderSpecifier<?> postSort(Pageable pageable) {
        return PagingUtil.getOrderSpecifier(pageable, QCommentaryPostEntity.commentaryPostEntity.createdAt, QCommentaryPostEntity.commentaryPostEntity.likeCount);
    }
}
