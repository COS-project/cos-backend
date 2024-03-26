package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateExam;
import com.cos.cercat.domain.ExamReview;
import com.cos.cercat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamReviewRepository extends JpaRepository<ExamReview, Long>, ExamReviewRepositoryCustom {

    Boolean existsExamReviewByUserAndCertificateExam(User user, CertificateExam certificateExam);

}
