package com.cos.cercat.repository;

import com.cos.cercat.domain.*;
import com.cos.cercat.dto.ExamReviewSearchCond;
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
public class ExamReviewRepositoryImpl implements ExamReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<ExamReview> searchExamReview(CertificateExamEntity certificateExamEntity, ExamReviewSearchCond cond, Pageable pageable) {

        List<ExamReview> contents = queryFactory.selectFrom(QExamReview.examReview)
                .leftJoin(QExamReview.examReview.certificateExamEntity, QCertificateExamEntity.certificateExamEntity)
                .where(
                        equalDifficulty(cond.examDifficulty()),
                        betweenPrepareMonths(cond.startMonths(), cond.endPreMonths()),
                        QExamReview.examReview.certificateExamEntity.eq(certificateExamEntity)
                )
                .orderBy(QExamReview.examReview.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(QExamReview.examReview.count())
                .from(QExamReview.examReview);

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    private BooleanExpression equalDifficulty(ExamDifficulty examDifficulty) {

        if (examDifficulty == null) {
            return null;
        }

        return QExamReview.examReview.examDifficulty.eq(examDifficulty);
    }

    private BooleanExpression betweenPrepareMonths(Integer startMonths, Integer endMonths) {

        if (startMonths == null && endMonths == null) {
            return null;
        }

        if (startMonths == null) {
            return QExamReview.examReview.prepareMonths.loe(endMonths);
        }

        if (endMonths == null) {
            return QExamReview.examReview.prepareMonths.goe(startMonths);
        }

        return QExamReview.examReview.prepareMonths.between(startMonths, endMonths);
    }
}
