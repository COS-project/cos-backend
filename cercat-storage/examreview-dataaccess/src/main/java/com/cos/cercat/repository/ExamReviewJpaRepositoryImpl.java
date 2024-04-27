package com.cos.cercat.repository;

import com.cos.cercat.domain.*;
import com.cos.cercat.examreview.ExamDifficulty;
import com.cos.cercat.examreview.ExamReviewSearchCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
@RequiredArgsConstructor
public class ExamReviewJpaRepositoryImpl implements ExamReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<ExamReviewEntity> searchExamReview(CertificateExamEntity certificateExamEntity, ExamReviewSearchCond cond, Pageable pageable) {

        List<ExamReviewEntity> contents = queryFactory.selectFrom(QExamReviewEntity.examReviewEntity)
                .leftJoin(QExamReviewEntity.examReviewEntity.certificateExamEntity, QCertificateExamEntity.certificateExamEntity)
                .where(
                        equalDifficulty(cond.examDifficulty()),
                        betweenPrepareMonths(cond.startMonths(), cond.endPreMonths()),
                        QExamReviewEntity.examReviewEntity.certificateExamEntity.eq(certificateExamEntity)
                )
                .orderBy(QExamReviewEntity.examReviewEntity.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(QExamReviewEntity.examReviewEntity.count())
                .from(QExamReviewEntity.examReviewEntity);

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    private BooleanExpression equalDifficulty(ExamDifficulty examDifficulty) {

        if (examDifficulty == null) {
            return null;
        }

        return QExamReviewEntity.examReviewEntity.examDifficulty.eq(examDifficulty);
    }

    private BooleanExpression betweenPrepareMonths(Integer startMonths, Integer endMonths) {

        if (startMonths == null && endMonths == null) {
            return null;
        }

        if (startMonths == null) {
            return QExamReviewEntity.examReviewEntity.prepareMonths.loe(endMonths);
        }

        if (endMonths == null) {
            return QExamReviewEntity.examReviewEntity.prepareMonths.goe(startMonths);
        }

        return QExamReviewEntity.examReviewEntity.prepareMonths.between(startMonths, endMonths);
    }
}
