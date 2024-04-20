package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateExamEntity;
import com.cos.cercat.domain.ExamReview;
import com.cos.cercat.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamReviewRepository extends JpaRepository<ExamReview, Long>, ExamReviewRepositoryCustom {

    Boolean existsExamReviewByUserEntityAndCertificateExamEntity(UserEntity userEntity, CertificateExamEntity certificateExamEntity);

}
