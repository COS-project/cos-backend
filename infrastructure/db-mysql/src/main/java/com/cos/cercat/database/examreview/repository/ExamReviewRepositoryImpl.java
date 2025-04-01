package com.cos.cercat.database.examreview.repository;

import com.cos.cercat.database.certificate.entity.CertificateExamEntity;
import com.cos.cercat.database.common.util.PagingUtil;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.database.examreview.entity.ExamReviewEntity;
import com.cos.cercat.domain.examreview.ExamReview;
import com.cos.cercat.domain.examreview.ExamReviewContent;
import com.cos.cercat.domain.examreview.ExamReviewRepository;
import com.cos.cercat.domain.examreview.ExamReviewSearchCond;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class ExamReviewRepositoryImpl implements ExamReviewRepository {

    private final ExamReviewJpaRepository examReviewJpaRepository;

    @Override
    public void save(User user,
                     CertificateExam recentExam,
                     ExamReviewContent content,
                     int prepareMonths) {

        ExamReviewEntity examReviewEntity = ExamReviewEntity.builder()
                .userEntity(UserEntity.from(user))
                .certificateExamEntity(CertificateExamEntity.from(recentExam))
                .examDifficulty(content.examDifficulty())
                .content(content.content())
                .prepareMonths(prepareMonths)
                .build();

        examReviewJpaRepository.save(examReviewEntity);
    }

    @Override
    public SliceResult<ExamReview> find(CertificateExam certificateExam, ExamReviewSearchCond cond, Cursor cursor) {
        Slice<ExamReviewEntity> examReviewEntities =
                examReviewJpaRepository.searchExamReview(CertificateExamEntity.from(certificateExam), cond, toPageRequest(cursor));

        return SliceResult.of(examReviewEntities.stream().map(ExamReviewEntity::toDomain).toList(), examReviewEntities.hasNext());
    }

    @Override
    public boolean existReview(User user, CertificateExam certificateExam) {
        return examReviewJpaRepository.existsUserIdAndCertificateExamId(user.getId(), certificateExam.id());
    }

    private PageRequest toPageRequest(Cursor cursor) {
        // 여러 정렬 기준을 처리할 Sort 객체 생성
        return PagingUtil.toPageRequest(cursor);
    }


}
