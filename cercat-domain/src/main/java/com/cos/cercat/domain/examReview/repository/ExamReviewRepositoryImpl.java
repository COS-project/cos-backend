package com.cos.cercat.domain.examReview.repository;

import com.cos.cercat.domain.certificate.domain.CertificateExam;
import com.cos.cercat.domain.certificate.domain.QCertificateExam;
import com.cos.cercat.domain.examReview.domain.ExamDifficulty;
import com.cos.cercat.domain.examReview.domain.ExamReview;
import com.cos.cercat.domain.examReview.dto.ExamReviewSearchCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cos.cercat.domain.examReview.domain.QExamReview.examReview;


@Repository
@RequiredArgsConstructor
public class ExamReviewRepositoryImpl implements ExamReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<ExamReview> searchExamReview(CertificateExam certificateExam, ExamReviewSearchCond cond, Pageable pageable) {

        List<ExamReview> contents = queryFactory.selectFrom(examReview)
                .leftJoin(examReview.certificateExam, QCertificateExam.certificateExam)
                .where(
                        equalDifficulty(cond.examDifficulty()),
                        betweenPrepareMonths(cond.startMonths(), cond.endPreMonths()),
                        examReview.certificateExam.eq(certificateExam)
                )
                .orderBy(examReview.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(examReview.count())
                .from(examReview);

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    private BooleanExpression equalDifficulty(ExamDifficulty examDifficulty) {

        if (examDifficulty == null) {
            return null;
        }

        return examReview.examDifficulty.eq(examDifficulty);
    }

    private BooleanExpression betweenPrepareMonths(Integer startMonths, Integer endMonths) {

        if (startMonths == null && endMonths == null) {
            return null;
        }

        if (startMonths == null) {
            return examReview.prepareMonths.loe(endMonths);
        }

        if (endMonths == null) {
            return examReview.prepareMonths.goe(startMonths);
        }

        return examReview.prepareMonths.between(startMonths, endMonths);
    }
}
