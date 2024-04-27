package com.cos.cercat.repository;

import com.cos.cercat.certificate.CertificateExam;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.CertificateExamEntity;
import com.cos.cercat.domain.ExamReviewEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.examreview.ExamReview;
import com.cos.cercat.examreview.ExamReviewContent;
import com.cos.cercat.examreview.ExamReviewRepository;
import com.cos.cercat.examreview.ExamReviewSearchCond;
import com.cos.cercat.user.User;
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
        Sort.Direction direction = Sort.Direction.fromOptionalString(cursor.sortDirection().name()).orElseThrow();
        return PageRequest.of(cursor.page(), cursor.size(), Sort.by(direction, cursor.sortKey()));
    }


}
