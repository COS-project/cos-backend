package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateExam;
import com.cos.cercat.domain.ExamReview;
import com.cos.cercat.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamReviewRepository extends JpaRepository<ExamReview, Long>, ExamReviewRepositoryCustom {

    Boolean existsExamReviewByUserEntityAndCertificateExam(UserEntity userEntity, CertificateExam certificateExam);

}
